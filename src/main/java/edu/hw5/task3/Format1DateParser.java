package edu.hw5.task3;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class Format1DateParser extends DateParser {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("y-M-d");

    @Override
    public Optional<LocalDate> parse(String dateString) {
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(dateString, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return (nextDateParser != null) ? nextDateParser.parse(dateString) : Optional.empty();
        }

        return Optional.of(localDate);
    }
}
