package edu.hw5;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.stream.Stream;
import static edu.hw5.Task6.isSubstring;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task6Test {
    private static final class NullArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of("hello", null),
                Arguments.of(null, "world"),
                Arguments.of(null, null)
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(NullArgumentsProvider.class)
    void isSubstring_NullArgumentsGiven_ThrowIllegalArgumentException(String s, String t) {
        assertThrows(IllegalArgumentException.class, () -> {
            isSubstring(s, t);
        });
    }

    @ParameterizedTest
    @CsvSource({"Hello World!, Wor", "_abacab_a, aca", "1pplication, 1ppl", "softwAre, wAre", "bus, bus", "#&#, #"})
    void isSubstring_tIsSubstringOfS_ReturnTrue(String s, String t) {
        boolean actualAnswer = isSubstring(s, t);
        assertThat(actualAnswer).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"abacaba, daba", "goose, game", "poly, polymorphism", "a, b"})
    void isSubstring_tIsNotSubstringOfS_ReturnFalse(String s, String t) {
        boolean actualAnswer = isSubstring(s, t);
        assertThat(actualAnswer).isFalse();
    }
}
