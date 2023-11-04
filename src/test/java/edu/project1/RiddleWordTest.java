package edu.project1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RiddleWordTest {
    @ParameterizedTest
    @ValueSource(strings = {"ab", "aba", "Abc", "ab3", "hello world"})
    void constructor_wordIsInvalid_throwIllegalArgumentException(String word) {
        assertThrows(IllegalArgumentException.class, () -> {
            new RiddleWord(word);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "abcba"})
    void constructor_wordIsValid_NoExceptionIsThrown(String word) {
        assertDoesNotThrow(() -> {
            new RiddleWord(word);
        });
    }
}
