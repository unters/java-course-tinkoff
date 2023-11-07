package edu.hw5;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import static edu.hw5.Task7.REGEX_1;
import static edu.hw5.Task7.REGEX_2;
import static edu.hw5.Task7.REGEX_3;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task7Test {
    private static final class RegexAndMatchingStringArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(REGEX_1, "000"),
                Arguments.of(REGEX_1, "010"),
                Arguments.of(REGEX_1, "100"),
                Arguments.of(REGEX_1, "110"),
                Arguments.of(REGEX_1, "01010101"),
                Arguments.of(REGEX_1, "11011111"),
                Arguments.of(REGEX_2, "0"),
                Arguments.of(REGEX_2, "1"),
                Arguments.of(REGEX_2, "00"),
                Arguments.of(REGEX_2, "11"),
                Arguments.of(REGEX_2, "101"),
                Arguments.of(REGEX_2, "010"),
                Arguments.of(REGEX_2, "01110"),
                Arguments.of(REGEX_2, "10101"),
                Arguments.of(REGEX_3, "0"),
                Arguments.of(REGEX_3, "1"),
                Arguments.of(REGEX_3, "00"),
                Arguments.of(REGEX_3, "01"),
                Arguments.of(REGEX_3, "10"),
                Arguments.of(REGEX_3, "11"),
                Arguments.of(REGEX_3, "000"),
                Arguments.of(REGEX_3, "001"),
                Arguments.of(REGEX_3, "010"),
                Arguments.of(REGEX_3, "011"),
                Arguments.of(REGEX_3, "100"),
                Arguments.of(REGEX_3, "101"),
                Arguments.of(REGEX_3, "110"),
                Arguments.of(REGEX_3, "111")
            );
        }
    }

    private static final class RegexAndNotMatchingStringArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(REGEX_1, "001"),
                Arguments.of(REGEX_1, "00100"),
                Arguments.of(REGEX_2, "100"),
                Arguments.of(REGEX_2, "1010"),
                Arguments.of(REGEX_2, "01101"),
                Arguments.of(REGEX_3, ""),
                Arguments.of(REGEX_3, "1101"),
                Arguments.of(REGEX_3, "10001")
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(RegexAndMatchingStringArgumentsProvider.class)
    void patternMatches_PairOfRegexWithMatchingStringGiven_ReturnTrue(String regex, String s) {
        boolean actualAnswer = Pattern.matches(regex, s);
        assertThat(actualAnswer).isTrue();
    }

    @ParameterizedTest
    @ArgumentsSource(RegexAndNotMatchingStringArgumentsProvider.class)
    void patternMatches_PairOfRegexWithNonMatchingStringGiven_ReturnFalse(String regex, String s) {
        boolean actualAnswer = Pattern.matches(regex, s);
        assertThat(actualAnswer).isFalse();
    }
}
