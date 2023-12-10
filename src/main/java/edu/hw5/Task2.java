package edu.hw5;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class Task2 {
    private static final int DESIRED_DAY_OF_MONTH = 13;

    private static final TemporalAdjuster NEXT_THIRTEENS_TEMPORAL_ADJUSTER =
        TemporalAdjusters.ofDateAdjuster(date -> date.plusMonths(1));
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final String INVALID_YEAR_MESSAGE = "year must be positive";

    public static List<String> getAllFridayThirteens(int year) {
        if (year <= 0) {
            throw new IllegalArgumentException(INVALID_YEAR_MESSAGE);
        }

        List<String> answer = new ArrayList<>();
        LocalDate dateTime = LocalDate.of(year, 1, DESIRED_DAY_OF_MONTH);
        do  {
            if (dateTime.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
                answer.add(dateTime.format(DATE_TIME_FORMATTER));
            }

            dateTime = dateTime.with(NEXT_THIRTEENS_TEMPORAL_ADJUSTER);
        } while (dateTime.getYear() == year);

        return answer;
    }

    private Task2() {
    }
}
