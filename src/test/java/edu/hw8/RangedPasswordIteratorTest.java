package edu.hw8;

import edu.hw8.task3.RangedPasswordIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RangedPasswordIteratorTest {
    private static class ValidArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                Arguments.of("abc", "a", "c", List.of("a", "b")),
                Arguments.of("abc", "aa", "bb", List.of("aa", "ab", "ac", "ba"))
            );
        }
    }

    private static class NullArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                Arguments.of(null, "aaa", "bbb"),
                Arguments.of("abc", null, "bbb"),
                Arguments.of("abc", "bbb", null),
                Arguments.of(null, null, null)
            );
        }
    }

    private static class InvalidPasswordsArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                Arguments.of("abc", "a", "bb"),
                Arguments.of("abc", "aa", "dd")
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(ValidArgumentsProvider.class)
    void new_ValidArgumentsGiven_IterationProducesExpectedResult(
        String alphabet,
        String password1,
        String password2,
        List<String> expectedAnswer
    ) {
        // given
        RangedPasswordIterator it = new RangedPasswordIterator(alphabet, password1, password2);

        // when
        List<String> actualAnswer =  new ArrayList<>();
        while (it.hasNext()) {
            actualAnswer.add(it.next());
        }

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }

    @ParameterizedTest
    @ArgumentsSource(NullArgumentsProvider.class)
    void new_NullAlphabetGiven_ThrowNullPointerException(String alphabet, String password1, String password2) {
        assertThrows(NullPointerException.class, () -> {
            new RangedPasswordIterator(alphabet, password1, password2);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(InvalidPasswordsArgumentsProvider.class)
    void new_InvalidPasswordsGiven_ThrowNullPointerException(String alphabet, String password1, String password2) {
        assertThrows(IllegalArgumentException.class, () -> {
            new RangedPasswordIterator(alphabet, password1, password2);
        });
    }
}
