package edu.project3;

import edu.project3.logstats.printer.LogsReportPrinter;
import java.nio.file.InvalidPathException;
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
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public record SessionParameters(String logsSource,
                                LogsSourceType logsSourceType,
                                Optional<LocalDateTime> from,
                                Optional<LocalDateTime> to,
                                LogsReportPrinter.FileFormat outputFileFormat,
                                Path logReportFile) {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final Options CLI_OPTIONS = CliOptions.getOptions();

    private static final String URL_REGEX = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}"
        + "\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    @SuppressWarnings("MultipleStringLiterals")
    static SessionParameters resolveSessionParameters(String[] args) {
        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cli = parser.parse(CLI_OPTIONS, args);

            String logsSource = cli.getOptionValue("path");
            LogsSourceType logsSourceType = resolveLogsSourceType(logsSource);
            Optional<LocalDateTime> from = cli.hasOption("from")
                ? Optional.of(resolveIso8601DateTime(cli.getOptionValue("from")))
                : Optional.empty();
            Optional<LocalDateTime> to = cli.hasOption("to")
                ? Optional.of(resolveIso8601DateTime(cli.getOptionValue("to")))
                : Optional.empty();
            LogsReportPrinter.FileFormat outputFileFormat = cli.hasOption("format")
                ? LogsReportPrinter.FileFormat.valueOf(cli.getOptionValue("format").toUpperCase())
                : LogsReportPrinter.FileFormat.MARKDOWN;
            Path logReportFile = cli.hasOption("saveto")
                ? Paths.get(cli.getOptionValue("saveto"))
                : createLogsReportFilePath(outputFileFormat);
            return new SessionParameters(logsSource, logsSourceType, from, to, outputFileFormat, logReportFile);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Wrong usage: " + e.getMessage());
        } catch (InvalidPathException e) {
            throw new IllegalArgumentException("invalid path to save logs to");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("illegal file format", e);
        }
    }

    private static LogsSourceType resolveLogsSourceType(String logsSource) {
        if (logsSource.contains("*") || logsSource.contains("[") || logsSource.contains("?")) {
            return LogsSourceType.WILDCARD;
        } else if (URL_PATTERN.matcher(logsSource).matches()) {
            return LogsSourceType.URL;
        }

        /* Probably, not the best solution to check if string is a filesystem path. Should I use regex instead?  */
        try {
            Path path = Paths.get(logsSource);
            return LogsSourceType.FILE;
        } catch (InvalidPathException e) {
            throw new IllegalArgumentException("Unknown resource type: " + logsSource);
        }
    }

    private static Path createLogsReportFilePath(LogsReportPrinter.FileFormat fileFormat) {
        String logsReportFileName = "logs_report_" + LocalDate.now() + fileFormat.getExtension();
        return Paths.get(System.getProperty("user.dir"), logsReportFileName);
    }

    /* Probably, I should have used regular expressions instead of try-catch circus.  */
    private static LocalDateTime resolveIso8601DateTime(String dateTime) {
        LocalDateTime localDateTime;
        try {
            TemporalAccessor temporalAccessor =
                DateTimeFormatter.ISO_DATE_TIME.parse(dateTime);
            localDateTime = LocalDateTime.from(temporalAccessor);
        } catch (DateTimeException dateTimeException) {
            try {
                TemporalAccessor temporalAccessor =
                    DateTimeFormatter.ISO_DATE.parse(dateTime);
                LocalDate localDate = LocalDate.from(temporalAccessor);
                localDateTime = localDate.atStartOfDay();
            } catch (DateTimeParseException dateTimeParseException) {
                throw new IllegalArgumentException(
                    "Invalid date-time format option: " + dateTime);
            }
        }

        LOGGER.info("Resolved LocalDateTime: " + localDateTime);
        return localDateTime;
    }

    protected enum LogsSourceType {
        FILE, WILDCARD, URL
    }
}
