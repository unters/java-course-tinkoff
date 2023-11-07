package edu.hw5;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import java.util.List;
import java.util.stream.Stream;
import static edu.hw5.Task2.getAllFridayThirteens;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task2Test {
    private static final class InvalidArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(0),
                Arguments.of(-1),
                Arguments.of(-1301),
                Arguments.of(-25726)
            );
        }
    }

    private static final class ValidArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(1925, List.of("1925-02-13", "1925-03-13", "1925-11-13")),
                Arguments.of(2023, List.of("2023-01-13", "2023-10-13")),
                Arguments.of(2024, List.of("2024-09-13", "2024-12-13"))
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(InvalidArgumentsProvider.class)
    void getAllFridays_InvalidYearGiven_ThrowIllegalArgumentException(int year) {
        assertThrows(IllegalArgumentException.class, () -> {
            getAllFridayThirteens(year);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(ValidArgumentsProvider.class)
    void getAllFridays_ValidYearGiven_ReturnExpectedAnswer(int year, List<String> expectedAnswer) {
        List<String> actualAnswer = getAllFridayThirteens(year);
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
