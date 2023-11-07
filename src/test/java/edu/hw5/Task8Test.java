package edu.hw5;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import static edu.hw5.Task8.REGEX_1;
import static edu.hw5.Task8.REGEX_2;
import static edu.hw5.Task8.REGEX_3;
import static edu.hw5.Task8.REGEX_4;
import static edu.hw5.Task8.REGEX_5;
import static edu.hw5.Task8.REGEX_6;
import static edu.hw5.Task8.REGEX_7;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task8Test {
    private static final class MatchingRegexAndStringArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(REGEX_1, "1"),
                Arguments.of(REGEX_1, "01101"),
                Arguments.of(REGEX_1, "111011010"),
                Arguments.of(REGEX_2, "0"),
                Arguments.of(REGEX_2, "10"),
                Arguments.of(REGEX_2, "01101"),
                Arguments.of(REGEX_2, "101001"),
                Arguments.of(REGEX_3, ""),
                Arguments.of(REGEX_3, "000"),
                Arguments.of(REGEX_3, "10010"),
                Arguments.of(REGEX_3, "0101100010"),
                Arguments.of(REGEX_4, "1"),
                Arguments.of(REGEX_4, "101"),
                Arguments.of(REGEX_4, "1111"),
                Arguments.of(REGEX_5, "1"),
                Arguments.of(REGEX_5, "10"),
                Arguments.of(REGEX_5, "101"),
                Arguments.of(REGEX_5, "1011101"),
                Arguments.of(REGEX_6, "100"),
                Arguments.of(REGEX_6, "001"),
                Arguments.of(REGEX_6, "010"),
                Arguments.of(REGEX_6, "001000"),
                Arguments.of(REGEX_7, "1"),
                Arguments.of(REGEX_7, "0"),
                Arguments.of(REGEX_7, "0101"),
                Arguments.of(REGEX_7, "00010100010")
            );
        }
    }

    private static final class NonMatchingRegexAndStringArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(REGEX_1, ""),
                Arguments.of(REGEX_1, "01"),
                Arguments.of(REGEX_1, "1110"),
                Arguments.of(REGEX_2, "00"),
                Arguments.of(REGEX_2, "101"),
                Arguments.of(REGEX_2, "011010"),
                Arguments.of(REGEX_2, "1010011"),
                Arguments.of(REGEX_3, "00"),
                Arguments.of(REGEX_3, "1010"),
                Arguments.of(REGEX_3, "010110001001"),
                Arguments.of(REGEX_4, "11"),
                Arguments.of(REGEX_4, "111"),
                Arguments.of(REGEX_5, "0"),
                Arguments.of(REGEX_5, "110"),
                Arguments.of(REGEX_5, "00111"),
                Arguments.of(REGEX_6, "0"),
                Arguments.of(REGEX_6, "11"),
                Arguments.of(REGEX_6, "0101"),
                Arguments.of(REGEX_6, "0111"),
                Arguments.of(REGEX_7, "11"),
                Arguments.of(REGEX_7, "01101"),
                Arguments.of(REGEX_7, "0001010110010")
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(MatchingRegexAndStringArgumentsProvider.class)
    void patternMatches_PairOfRegexWithMatchingStringGiven_ReturnTrue(String regex, String s) {
        boolean actualAnswer = Pattern.matches(regex, s);
        assertThat(actualAnswer).isTrue();
    }


    @ParameterizedTest
    @ArgumentsSource(NonMatchingRegexAndStringArgumentsProvider.class)
    void patternMatches_PairOfRegexWithNonMatchingStringGiven_ReturnTrue(String regex, String s) {
        boolean actualAnswer = Pattern.matches(regex, s);
        assertThat(actualAnswer).isFalse();
    }
}
