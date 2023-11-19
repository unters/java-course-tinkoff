package edu.project3;

import edu.project3.logstats.LogRecord;
import edu.project3.logstats.LogsReport;
import edu.project3.logstats.printer.AdocPrinter;
import edu.project3.logstats.printer.LogsReportPrinter;
import edu.project3.logstats.printer.MarkdownPrinter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static edu.project3.logstats.printer.LogsReportPrinter.FileFormat;

@SuppressWarnings({"MultipleStringLiterals", "RegexpSinglelineJava"})
public class Application {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final Options CLI_OPTIONS = new Options();

    static {
        CLI_OPTIONS.addOption(Option.builder(null).longOpt("path")
            .hasArgs()
            .required(true)
            .desc("Path to log file.")
            .build()
        );
        CLI_OPTIONS.addOption(null, "from", true, "From description");
        CLI_OPTIONS.addOption(null, "to", true, "To description");
        CLI_OPTIONS.addOption(null, "format", true, "File format.");
    }

    @SuppressWarnings("ReturnCount")
    public static void run(String[] args) {
        SessionParameters sessionParameters;
        try {
            sessionParameters = parseSessionParameters(args);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("logstats", CLI_OPTIONS);
            return;
        }

        LogsReport logsReport = new LogsReport();
        try (Stream<LogRecord> logRecordStream = readLogs(sessionParameters)) {
            logsReport.update(logRecordStream);
            LOGGER.info(logsReport);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        try {
            Path logsReportFile = getLogsReportPath(sessionParameters);
            LogsReportPrinter logsReportPrinter = switch (sessionParameters.outputFileFormat) {
                case MARKDOWN -> new MarkdownPrinter(logsReport, sessionParameters);
                case ADOC -> new AdocPrinter(logsReport, sessionParameters);
            };
            logsReportPrinter.print(logsReportFile);
        } catch (IOException e) {
            System.out.println("Error printing log report: " + e.getMessage());
            return;
        }
    }

    private static SessionParameters parseSessionParameters(String[] args) {
        String logsFile;
        LogsSourceType resourceType;
        Optional<LocalDateTime> from = Optional.empty();
        Optional<LocalDateTime> to = Optional.empty();
        FileFormat outputFileFormat = FileFormat.MARKDOWN;
        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cli = parser.parse(CLI_OPTIONS, args);
            LOGGER.info(cli.getOptionValue("path"));

            logsFile = cli.getOptionValue("path");
            resourceType = resolveResourceType(logsFile);
            if (resourceType == null) {
                throw new IllegalArgumentException("Unknown source format: " + logsFile);
            }

            if (cli.hasOption("from")) {
                try {
                    TemporalAccessor temporalAccessor =
                        DateTimeFormatter.ISO_DATE_TIME.parse(cli.getOptionValue("from"));
                    from = Optional.of(LocalDateTime.from(temporalAccessor));
                    LOGGER.info("from: " + from.get());
                } catch (DateTimeException dateTimeException) {
                    try {
                        TemporalAccessor temporalAccessor =
                            DateTimeFormatter.ISO_DATE.parse(cli.getOptionValue("from"));
                        LocalDate localDate = LocalDate.from(temporalAccessor);
                        from = Optional.of(localDate.atStartOfDay());
                        LOGGER.info("from: " + from.get());
                    } catch (DateTimeParseException dateTimeParseException) {
                        throw new IllegalArgumentException(
                            "Invalid date/time format for \"from\" option: " + cli.getOptionValue("from"));
                    }
                }
            }

            if (cli.hasOption("to")) {
                try {
                    TemporalAccessor temporalAccessor =
                        DateTimeFormatter.ISO_DATE_TIME.parse(cli.getOptionValue("to"));
                    to = Optional.of(LocalDateTime.from(temporalAccessor));
                    LOGGER.info("to: " + to.get());
                } catch (DateTimeParseException e) {
                    try {
                        TemporalAccessor temporalAccessor =
                            DateTimeFormatter.ISO_DATE.parse(cli.getOptionValue("to"));
                        LocalDate localDate = LocalDate.from(temporalAccessor);
                        to = Optional.of(localDate.atStartOfDay());
                        LOGGER.info("to: " + to.get());
                    } catch (DateTimeParseException dateTimeParseException) {
                        throw new IllegalArgumentException(
                            "Invalid date/time format for \"to\" option: " + cli.getOptionValue("to"));
                    }
                }
            }

            if (cli.hasOption("format")) {
                try {
                    outputFileFormat = FileFormat.valueOf(cli.getOptionValue("format").toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Unsupported file format: " + cli.getOptionValue("format"));
                }
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Wrong usage: " + e.getMessage());
        }

        return new SessionParameters(logsFile, resourceType, from, to, outputFileFormat);
    }

    private static LogsSourceType resolveResourceType(String resource) {
        if (LogsSourceType.LINUX_RELATIVE_PATH.pattern.matcher(resource).matches()) {
            return LogsSourceType.LINUX_RELATIVE_PATH;
        } else if (LogsSourceType.URL.pattern.matcher(resource).matches()) {
            return LogsSourceType.URL;  // Not supported yet.
        }
        return null;
    }

    private static Stream<LogRecord> readLogs(SessionParameters sessionParameters) throws IOException {
        Stream<LogRecord> logRecordStream = switch (sessionParameters.logsSourceType) {
            case LINUX_RELATIVE_PATH -> readLogsFromLocalFile(Paths.get(sessionParameters.logsSource));
            case URL -> readLogsUsingUrl(); // Not supported yet.
        };

        if (sessionParameters.from().isPresent()) {
            logRecordStream = logRecordStream.filter(logRecord -> {
                LocalDateTime logRecordDateAndTime = logRecord.getLocalDateTime();
                return logRecordDateAndTime.isAfter(sessionParameters.from().get());
            });
        }

        if (sessionParameters.to().isPresent()) {
            logRecordStream = logRecordStream.filter(logRecord -> {
                LocalDateTime logRecordDateAndTime = logRecord.getLocalDateTime();
                return logRecordDateAndTime.isBefore(sessionParameters.to().get());
            });
        }

        return logRecordStream;
    }

    private static Stream<LogRecord> readLogsFromLocalFile(Path logsFile) throws IOException {
        return Files.lines(logsFile).map(LogRecord::new);
    }

    private static Stream<LogRecord> readLogsUsingUrl() {
        return null;    // TODO.
    }

    private static Path getLogsReportPath(SessionParameters sessionParameters) {
        String logsReportFileName =
            "logs_report_" + LocalDate.now() + sessionParameters.outputFileFormat().getExtension();
        return switch (sessionParameters.logsSourceType) {
            case LINUX_RELATIVE_PATH -> {
                Path parentDirectory = Paths.get(sessionParameters.logsSource).getParent();
                yield Paths.get(parentDirectory.toString(), logsReportFileName);
            }
            case URL -> null;   // TODO.
        };
    }

    private Application() {
    }

    public record SessionParameters(String logsSource,
                                    LogsSourceType logsSourceType,
                                    Optional<LocalDateTime> from,
                                    Optional<LocalDateTime> to,
                                    FileFormat outputFileFormat) {
    }

    private enum LogsSourceType {
        LINUX_RELATIVE_PATH("^(.+)\\/([^\\/]+)$"),
        URL("");    // TODO.

        LogsSourceType(String regex) {
            this.regex = regex;
            pattern = Pattern.compile(this.regex);
        }

        private final String regex;
        public final Pattern pattern;
    }
}
