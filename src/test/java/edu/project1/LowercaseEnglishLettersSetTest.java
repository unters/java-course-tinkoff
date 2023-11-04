package edu.project1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LowercaseEnglishLettersSetTest {
    private static final int NUMBER_OF_LETTERS_IN_ENGLISH_ALPHABET = 26;

    @ParameterizedTest
    @ValueSource(strings = {"Abc", "1bc", "hello world"})
    void oneArgumentConstructor_InvalidStringPassed_ThrowIllegalArgumentException(String s) {
        assertThrows(IllegalArgumentException.class, () -> {
            new LowercaseEnglishLettersSet(s);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "ab", "abc"})
    void oneArgumentConstructor_ValidStringPassed_NoExceptionIsThrown(String s) {
        assertDoesNotThrow(() -> {
            new LowercaseEnglishLettersSet(s);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello", "aaaa", "kevin"})
    void contains_ObjectCreatedUsingOneArgumentConstructor_ReturnsTrue(String word) {
        LowercaseEnglishLettersSet lettersSet = new LowercaseEnglishLettersSet(word);
        for (int i = 0; i < NUMBER_OF_LETTERS_IN_ENGLISH_ALPHABET; ++i) {
            char c = (char) ('a' + i);
            boolean letterIsPresent = (word.indexOf(c) != -1);
            boolean containmentFlag = lettersSet.contains(c);
            assertThat(containmentFlag).isEqualTo(letterIsPresent);
        }
    }

    @ParameterizedTest
    @ValueSource(chars = {'a', 'b', 'c'})
    void contains_SingleCharacterAddedIntoEmptySet_ReturnExpectedAnswers(char c) {
        LowercaseEnglishLettersSet lettersSet = new LowercaseEnglishLettersSet();
        lettersSet.add(c);
        for (int i = 0; i < NUMBER_OF_LETTERS_IN_ENGLISH_ALPHABET; ++i) {
            char cc = (char) ('a' + i);
            assertThat(lettersSet.contains(cc)).isEqualTo(cc == c);
        }
    }
}
