package edu.hw5.task3;

import java.time.LocalDate;
import java.util.Optional;

/* Chain of responsibility.  */
public abstract class DateParser {
    public void setNextParser(DateParser dateParser) {
        nextDateParser = dateParser;
    }

    public abstract Optional<LocalDate> parse(String dateString);

    protected DateParser nextDateParser = null;
}
