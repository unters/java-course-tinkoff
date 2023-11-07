package edu.hw5;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task1 {
    private static final String DATETIME_REGEX = "\\d{4}-\\d{2}-\\d{2}, \\d{2}:\\d{2}";
    private static final String DATETIME_FORMAT = "yyyy-MM-dd, HH:mm";
    private static final String DATETIME_SEPARATOR = " - ";
    private static final String PERIOD_REGEX =
        "^(" + DATETIME_REGEX + ")" + DATETIME_SEPARATOR + "(" + DATETIME_REGEX + ")$";

    private static final Pattern PERIOD_PATTERN = Pattern.compile(PERIOD_REGEX);
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_FORMAT);

    private static final String PARSING_FAILED_MESSAGE =
        "given string does not match the required pattern";
    private static final String INVALID_PERIOD_DATA_MESSAGE =
        "invalid period - session could not begin before it has ended";

    public static Duration calculateAverageTime(List<String> periodStrings) {
        long totalMinutes = 0;
        for (String periodString : periodStrings) {
            Matcher matcher = PERIOD_PATTERN.matcher(periodString);
            if (!matcher.matches()) {
                throw new IllegalArgumentException(PARSING_FAILED_MESSAGE);
            }

            LocalDateTime sessionStart = LocalDateTime.parse(matcher.group(1), DATETIME_FORMATTER);
            LocalDateTime sessionEnd = LocalDateTime.parse(matcher.group(2), DATETIME_FORMATTER);
            if (sessionStart.isAfter(sessionEnd)) {
                throw new IllegalArgumentException(INVALID_PERIOD_DATA_MESSAGE);
            }

            Duration sessionDuration = Duration.between(sessionStart, sessionEnd);
            totalMinutes += sessionDuration.toMinutes();
        }

        return Duration.ofMinutes(totalMinutes).dividedBy(periodStrings.size());
    }

    private Task1() {
    }
}
