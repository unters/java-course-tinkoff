package edu.hw5.task3;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Format4DateParser extends DateParser {
    private static final String DATE_REGEX =
        "^(0 days ago)|(1 day ago)|([2-9] days ago)|([1-9][0-9]+ days ago)$";
    private static final Pattern DATE_PATTERN = Pattern.compile(DATE_REGEX);

    @Override
    public Optional<LocalDate> parse(String dateString) {
        Matcher matcher = DATE_PATTERN.matcher(dateString);
        if (matcher.matches()) {
            int offset = Integer.parseInt(dateString.substring(0, dateString.indexOf(" ")));
            return Optional.of(LocalDate.now().minusDays(offset));
        }

        return (nextDateParser == null) ? Optional.empty() : nextDateParser.parse(dateString);
    }
}
