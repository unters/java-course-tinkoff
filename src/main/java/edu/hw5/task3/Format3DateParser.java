package edu.hw5.task3;

import java.time.LocalDate;
import java.util.Optional;

public class Format3DateParser extends DateParser {
    @Override
    public Optional<LocalDate> parse(String dateString) {
        if ("tomorrow".equals(dateString)) {
            return Optional.of(LocalDate.now().plusDays(1));
        }

        if ("today".equals(dateString)) {
            return Optional.of(LocalDate.now());
        }

        if ("yesterday".equals(dateString)) {
            return Optional.of(LocalDate.now().minusDays(1));
        }

        return (nextDateParser == null) ? Optional.empty() : nextDateParser.parse(dateString);
    }
}
