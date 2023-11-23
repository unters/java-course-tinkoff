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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public record SessionParameters(String logsSource,
                                LogsSourceType logsSourceType,
                                Optional<LocalDateTime> from,
                                Optional<LocalDateTime> to,
                                LogsReportPrinter.FileFormat outputFileFormat,
                                Path logReportFile) {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String URL_REGEX = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}"
        + "\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    static LogsSourceType resolveLogsSourceType(String logsSource) {
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

    /* Probably, I should have used regular expressions instead of try-catch circus.  */
    static LocalDateTime resolveIso8601DateTime(String dateTime) {
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
