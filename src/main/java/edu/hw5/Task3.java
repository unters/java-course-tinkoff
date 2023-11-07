package edu.hw5;

import edu.hw5.task3.DateParser;
import edu.hw5.task3.Format1DateParser;
import edu.hw5.task3.Format2DateParser;
import edu.hw5.task3.Format3DateParser;
import edu.hw5.task3.Format4DateParser;
import java.time.LocalDate;
import java.util.Optional;

public class Task3 {
    private static final DateParser DATE_PARSER;

    private static final String NULL_STRING_GIVEN_MESSAGE = "dateString cannot be null";

    static {
        /* Chain of responsibility.  */
        Format1DateParser format1DateParser = new Format1DateParser();
        Format2DateParser format2DateParser = new Format2DateParser();
        Format3DateParser format3DateParser = new Format3DateParser();
        Format4DateParser format4DateParser = new Format4DateParser();
        format1DateParser.setNextParser(format2DateParser);
        format2DateParser.setNextParser(format3DateParser);
        format3DateParser.setNextParser(format4DateParser);
        DATE_PARSER = format1DateParser;
    }

    public static Optional<LocalDate> parseDateString(String dateString) {
        if (dateString == null) {
            throw new IllegalArgumentException(NULL_STRING_GIVEN_MESSAGE);
        }
        return DATE_PARSER.parse(dateString);
    }

    private Task3() {
    }
}
