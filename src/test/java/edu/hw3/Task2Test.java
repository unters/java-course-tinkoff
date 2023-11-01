package edu.hw3;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.List;
import java.util.stream.Stream;
import static edu.hw3.Task2.clusterizeString;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task2Test {
    private static final class ValidParenthesesStringArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of("()", List.of(new String[] {"()"})),
                Arguments.of("()()()", List.of(new String[] {"()", "()", "()"})),
                Arguments.of("(())()(()(()))", List.of(new String[] {"(())", "()", "(()(()))"}))
            );
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"(", ")", "((", "))", "())", "(()", "()())("})
    void clusterizeString_InvalidParenthesesStringPassed_ThrowIllegalArgumentException(String s) {
        assertThrows(IllegalArgumentException.class, () -> {
            clusterizeString(s);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"(hi)", "Hello World!", "(1 + 19) * 2"})
    void clusterizeString_InvalidStringPassed_ThrowIllegalArgumentException(String s) {
        assertThrows(IllegalArgumentException.class, () -> {
            clusterizeString(s);
        });
    }

    @ParameterizedTest
    @NullSource
    void clusterizeString_NullArgumentPassed_ThrowIllegalArgumentException(String s) {
        assertThrows(IllegalArgumentException.class, () -> {
            clusterizeString(s);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(ValidParenthesesStringArgumentsProvider.class)
    void clusterizeString_InvalidStringPassed_ThrowIllegalArgumentException(String s, List<String> expectedAnswer) {
        List<String> actualAnswer = clusterizeString(s);
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
