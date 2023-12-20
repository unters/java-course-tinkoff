package edu.hw8;

import edu.hw8.task3.PasswordIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PasswordIteratorTest {
    private static class ValidArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                Arguments.of("abc", 1, List.of("a", "b", "c")),
                Arguments.of("abc", 2, List.of("aa", "ab", "ac", "ba", "bb", "bc", "ca", "cb", "cc"))
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(ValidArgumentsProvider.class)
    void new_ValidArgumentsGiven_IterationProducesExpectedResult(
        String alphabet,
        int expectedLength,
        List<String> expectedAnswer
    ) {
        // given
        PasswordIterator it = new PasswordIterator(alphabet, expectedLength);

        // when
        List<String> actualAnswer =  new ArrayList<>();
        while (it.hasNext()) {
            actualAnswer.add(it.next());
        }

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1})
    void new_NullAlphabetGiven_ThrowNullPointerException(int expectedLength) {
        assertThrows(NullPointerException.class, () -> {
            new PasswordIterator(null, expectedLength);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, -5, -1, 0})
    void new_NonPositiveExpectedLengthGiven_ThrowIllegalArgumentException(int expectedLength) {
        // given
        String alphabet = "0123456789";
        assertThrows(IllegalArgumentException.class, () -> {
            new PasswordIterator(alphabet, expectedLength);
        });
    }
}
