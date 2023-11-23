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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static edu.project3.SessionParameters.LogsSourceType;
import static edu.project3.logstats.printer.LogsReportPrinter.FileFormat;

@SuppressWarnings({"MultipleStringLiterals", "RegexpSinglelineJava"})
@UtilityClass
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
        CLI_OPTIONS.addOption(
            null,
            "from",
            true,
            "ISO8601 date (and time) from which logs will be considered in the statistics."
        );
        CLI_OPTIONS.addOption(
            null,
            "to",
            true,
            "ISO8601 date (and time) until which logs will be considered in the statistics."
        );
        CLI_OPTIONS.addOption(null, "format", true, "File format.");
        CLI_OPTIONS.addOption(null, "saveto", true, "Path to file, where log statistics will be saved.");
    }

    @SuppressWarnings("ReturnCount")
    public static void run(String[] args) {
        SessionParameters sessionParameters;
        try {
            sessionParameters = resolveSessionParameters(args);
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
            LogsReportPrinter logsReportPrinter = switch (sessionParameters.outputFileFormat()) {
                case MARKDOWN -> new MarkdownPrinter(logsReport, sessionParameters);
                case ADOC -> new AdocPrinter(logsReport, sessionParameters);
            };
            logsReportPrinter.print(sessionParameters.logReportFile());
        } catch (IOException e) {
            System.out.println("Error printing log report: " + e.getMessage());
            return;
        }
    }

    private static SessionParameters resolveSessionParameters(String[] args) {
        String logsSource;
        LogsSourceType logsSourceType;
        Optional<LocalDateTime> from;
        Optional<LocalDateTime> to;
        FileFormat outputFileFormat;
        Path logReportFile;
        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cli = parser.parse(CLI_OPTIONS, args);

            logsSource = cli.getOptionValue("path");
            LOGGER.info("Resolved logsSource: " + cli.getOptionValue("path"));

            logsSourceType = SessionParameters.resolveLogsSourceType(logsSource);
            LOGGER.info("Resolved logsSourceType: " + logsSourceType);

            if (cli.hasOption("from")) {
                from = Optional.of(SessionParameters.resolveIso8601DateTime(cli.getOptionValue("from")));
                LOGGER.info("Resolved from: " + from);
            } else {
                from = Optional.empty();
            }

            if (cli.hasOption("to")) {
                to = Optional.of(SessionParameters.resolveIso8601DateTime(cli.getOptionValue("to")));
                LOGGER.info("Resolved to: " + to);
            } else {
                to = Optional.empty();
            }

            if (cli.hasOption("format")) {
                try {
                    outputFileFormat = FileFormat.valueOf(cli.getOptionValue("format").toUpperCase());
                    LOGGER.info("Resolver outputFileFormat: " + outputFileFormat);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Unsupported file format: " + cli.getOptionValue("format"));
                }
            } else {
                outputFileFormat = FileFormat.MARKDOWN;
            }

            if (cli.hasOption("saveto")) {
                logReportFile = Paths.get(cli.getOptionValue("saveto"));
            } else {
                String logsReportFileName =
                    "logs_report_" + LocalDate.now() + outputFileFormat.getExtension();
                logReportFile =  Paths.get(System.getProperty("user.dir"), logsReportFileName);
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Wrong usage: " + e.getMessage());
        }

        return new SessionParameters(logsSource, logsSourceType, from, to, outputFileFormat, logReportFile);
    }

    private static Stream<LogRecord> readLogs(SessionParameters sessionParameters) throws IOException {
        Stream<LogRecord> logRecordStream = switch (sessionParameters.logsSourceType()) {
            case FILE -> {
                Path logsFilePath = Paths.get(sessionParameters.logsSource());
                if (!logsFilePath.isAbsolute()) {
                    logsFilePath = Paths.get(System.getProperty("user.dir"), logsFilePath.toString());
                }

                yield readLogsFromFile(logsFilePath);
            }

            case WILDCARD -> {
                List<Path> logsFiles = resolveWildcardFilePaths(sessionParameters.logsSource());
                yield readLogsFromFiles(logsFiles);
            }
            case URL -> readLogsUsingUrl(sessionParameters.logsSource());
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

    /* Works for linux paths only. Should I add methods to make it easier to add support for other operational
     * systems?  */
    private static List<Path> resolveWildcardFilePaths(String logsFilesWildcard) throws IOException {
        int starIndex = logsFilesWildcard.indexOf('*');
        int questionMarkIndex = logsFilesWildcard.indexOf('?');
        int openingBracketIndex = logsFilesWildcard.indexOf('[');
        int minIndexOfSpecialCharacter = Integer.MAX_VALUE;
        if (starIndex != -1) {
            minIndexOfSpecialCharacter = Math.min(minIndexOfSpecialCharacter, starIndex);
        }
        if (questionMarkIndex != -1) {
            minIndexOfSpecialCharacter = Math.min(minIndexOfSpecialCharacter, questionMarkIndex);
        }
        if (openingBracketIndex != -1) {
            minIndexOfSpecialCharacter = Math.min(minIndexOfSpecialCharacter, openingBracketIndex);
        }

        int slashIndex =
            logsFilesWildcard.substring(0, Math.min(logsFilesWildcard.length(), minIndexOfSpecialCharacter))
                .lastIndexOf('/');
        Path directory;
        String pattern;
        if (slashIndex == -1) {
            directory = Paths.get(System.getProperty("user.dir"));
            pattern = logsFilesWildcard;
        } else {
            pattern = logsFilesWildcard.substring(slashIndex + 1);
            directory = Paths.get(logsFilesWildcard.substring(0, slashIndex));
            if (!directory.isAbsolute()) {
                directory = Paths.get(System.getProperty("user.dir"), directory.toString());
            }
        }

        WildcardPathFinder wildcardPathFinder = new WildcardPathFinder(pattern);
        Files.walkFileTree(directory, wildcardPathFinder);
        List<Path> logsFiles = wildcardPathFinder.done();
        for (int i = 0; i < logsFiles.size(); ++i) {
            logsFiles.set(i, directory.resolve(logsFiles.get(i)));
        }
        LOGGER.info("Matching files found: " + Arrays.toString(logsFiles.toArray()));
        return logsFiles;
    }

    private static Stream<LogRecord> readLogsFromFile(Path logsFile) throws IOException {
        return Files.lines(logsFile).map(LogRecord::new);
    }

    private static Stream<LogRecord> readLogsFromFiles(List<Path> logsFiles) throws IOException {
        List<Stream<LogRecord>> logRecordStreams = new ArrayList<>();
        for (Path logsFile : logsFiles) {
            Stream<LogRecord> logRecordStream = Files.lines((logsFile)).map(LogRecord::new);
            logRecordStreams.add(logRecordStream);
        }

        Stream<LogRecord> resultingLogRecordStream = logRecordStreams.get(0);
        for (int i = 1; i < logRecordStreams.size(); ++i) {
            resultingLogRecordStream = Stream.concat(resultingLogRecordStream, logRecordStreams.get(i));
        }

        return resultingLogRecordStream;
    }

    private static Stream<LogRecord> readLogsUsingUrl(String url) {
        return null;    // TODO.
    }

    private static Path getLogsReportPath(SessionParameters sessionParameters) {
        String logsReportFileName =
            "logs_report_" + LocalDate.now() + sessionParameters.outputFileFormat().getExtension();
        return Paths.get(System.getProperty("user.dir"), logsReportFileName);
    }
}
