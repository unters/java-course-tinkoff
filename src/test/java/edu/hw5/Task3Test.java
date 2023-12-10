package edu.hw5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;
import static edu.hw5.Task3.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task3Test {
    private static final class ValidFormatDateStringArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of("2023-11-07", Optional.of(LocalDate.of(2023, 11, 7))),
                Arguments.of("2023-12-2", Optional.of(LocalDate.of(2023, 12, 2))),
                Arguments.of("1/3/1976", Optional.of(LocalDate.of(1976, 3, 1))),
                Arguments.of("1/3/20", Optional.of(LocalDate.of(20, 3, 1))),
                Arguments.of("tomorrow", Optional.of(LocalDate.now().plusDays(1))),
                Arguments.of("today", Optional.of(LocalDate.now())),
                Arguments.of("yesterday", Optional.of(LocalDate.now().minusDays(1))),
                Arguments.of("0 days ago", Optional.of(LocalDate.now())),
                Arguments.of("1 day ago", Optional.of(LocalDate.now().minusDays(1))),
                Arguments.of("2 days ago", Optional.of(LocalDate.now().minusDays(2))),
                Arguments.of("2234 days ago", Optional.of(LocalDate.now().minusDays(2234)))
            );
        }
    }

    @Test
    void parseDateString_NullStringGiven_ThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            parseDateString(null);
        });
    }

    @ParameterizedTest
    @CsvSource({"0 day ago", "1 days ago", "next week", "13-01-2001", "-13 days ago"})
    void parseDateString_DateStringOfInvalidFormatGiven_ReturnEmptyOptional(String dateString)
    {
        Optional<LocalDate> actualAnswer = parseDateString(dateString);
        assertThat(actualAnswer.isEmpty()).isTrue();
    }

    @ParameterizedTest
    @ArgumentsSource(ValidFormatDateStringArgumentsProvider.class)
    void parseDateString_DateStringOfValidFormatGiven_ReturnNonEmptyOptionalWithExpectedAnswer(
        String dateString,
        Optional<LocalDate> expectedAnswer
    ) {
        Optional<LocalDate> actualAnswer = parseDateString(dateString);
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
