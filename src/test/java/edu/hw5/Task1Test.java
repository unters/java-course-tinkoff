package edu.hw5;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Stream;
import static edu.hw5.Task1.calculateAverageTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task1Test {
    private static final class InvalidPeriodFormatArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(List.of("12-05-2023, 20:23:15 - 23-05-2023, 21:23:15")),
                Arguments.of(List.of("12-05-2023T20:23 - 23-05-2023T21:23")),
                Arguments.of(List.of("01:30:12")),
                Arguments.of(List.of("Hello World!"))
            );
        }
    }

    private static final class InvalidDateArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(List.of("2023-05-12, 20:23 - 2023-05-12, 20:63")),
                Arguments.of(List.of("2023-05-12, 20:23 - 2023-05-11, 24:01")),
                Arguments.of(List.of("2023-12-31, 23:23 - 2023-12-32, 00:23")),
                Arguments.of(List.of("2023-12-31, 23:23 - 2022-13-01, 00:23"))
            );
        }
    }

    private static final class InvalidPeriodsArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(List.of("2023-05-12, 20:23 - 2023-05-12, 20:13")),
                Arguments.of(List.of("2023-05-12, 20:23 - 2023-05-12, 19:23")),
                Arguments.of(List.of("2023-05-12, 20:23 - 2023-05-11, 21:23")),
                Arguments.of(List.of("2023-05-12, 20:23 - 2023-04-12, 21:23")),
                Arguments.of(List.of("2023-05-12, 20:23 - 2022-05-12, 21:23"))
            );
        }
    }

    private static final class ValidPeriodsArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(List.of(
                    "2023-05-12, 20:23 - 2023-05-12, 21:23",
                    "2023-05-12, 20:51 - 2023-05-12, 22:21",
                    "2023-05-12, 23:47 - 2023-05-13, 00:17"
                ), Duration.ofMinutes(60)),
                Arguments.of(List.of(
                    "2021-12-31, 23:59 - 2022-01-01, 00:01"
                ), Duration.ofMinutes(2))
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(InvalidPeriodFormatArgumentsProvider.class)
    void calculateAverageTime_InvalidPeriodFormatParametersGiven_ThrowIllegalArgumentsException(
        List<String> periodStrings
    ) {
        assertThrows(IllegalArgumentException.class, () -> {
            calculateAverageTime(periodStrings);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(InvalidDateArgumentsProvider.class)
    void calculateAverageTime_InvalidDateParametersGiven_ThrowDateTimeParseException(List<String> periodStrings) {
        assertThrows(DateTimeParseException.class, () -> {
            calculateAverageTime(periodStrings);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(InvalidPeriodsArgumentsProvider.class)
    void calculateAverageTime_InvalidPeriodParametersGiven_ThrowIllegalArgumentsException(List<String> periodStrings) {
        assertThrows(IllegalArgumentException.class, () -> {
            calculateAverageTime(periodStrings);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(ValidPeriodsArgumentsProvider.class)
    void calculateAverageTime_ValidDataParametersGiven_ReturnExpectedAnswer(
        List<String> periodStrings,
        Duration expectedAnswer
    ) {
        Duration actualAnswer = calculateAverageTime(periodStrings);
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
