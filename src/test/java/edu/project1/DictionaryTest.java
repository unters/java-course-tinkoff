package edu.project1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DictionaryTest {
    @ParameterizedTest
    @ValueSource(strings = {"apple kettle Anna", "apple k3ttle price", "abc cde fg"})
    void constructor_inputStreamContainsInvalidWords_ThrowIllegalArgumentException(String words) throws IOException {
        try (InputStream inputStream = new ByteArrayInputStream(words.getBytes())) {
            assertThrows(IllegalArgumentException.class, () -> {
                new Dictionary(inputStream);
            });
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"apple kettle bottle", "apple"})
    void constructor_inputStreamContainsValidWords_NoExceptionIsThrown(String words) throws IOException {
        try (InputStream inputStream = new ByteArrayInputStream(words.getBytes())) {
            assertDoesNotThrow(() -> {
                new Dictionary(inputStream);
            });
        }
    }

    @Test
    void getRandomWord_methodCalled_NoExceptionIsThrown() throws IOException {
        String words = "book apple kettle experience wheel building paradox limit logarithm";
        try (InputStream inputStream = new ByteArrayInputStream(words.getBytes())) {
            Dictionary dictionary = new Dictionary(inputStream);
            for (int i = 0; i < 100; ++i) {
                assertDoesNotThrow(dictionary::getRandomWord);
            }
        }
    }
}
