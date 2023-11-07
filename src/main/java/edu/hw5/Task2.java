package edu.hw5;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class Task2 {
    private static final int DESIRED_DAY_OF_MONTH = 13;

    private static final TemporalAdjuster NEXT_FRIDAY_TEMPORAL_ADJUSTER = TemporalAdjusters.next(DayOfWeek.FRIDAY);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final String INVALID_YEAR_MESSAGE = "year must be positive";

    public static List<String> getAllFridayThirteens(int year) {
        if (year <= 0) {
            throw new IllegalArgumentException(INVALID_YEAR_MESSAGE);
        }

        List<String> answer = new ArrayList<>();
        LocalDateTime dateTime = LocalDateTime.of(year, 1, 1, 0, 0);
        while (dateTime.getYear() == year) {
            dateTime = dateTime.with(NEXT_FRIDAY_TEMPORAL_ADJUSTER);
            if (dateTime.getDayOfMonth() == DESIRED_DAY_OF_MONTH) {
                answer.add(dateTime.format(DATE_TIME_FORMATTER));
            }
        }

        return answer;
    }

    private Task2() {
    }
}
