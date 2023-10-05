package edu.hw1;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task4Test {
    @Test void fixString_InputStringIsNull_ThrowException() {
        // given
        String s = null;

        // when
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            Task4.fixString(s);
        });

        // then
        assertThat(e.getMessage()).contains("cannot be null");
    }

    @Test void fixString_InputStringIsEmpty_ReturnEmptyString() {
        // given
        String s = "";

        // when
        String actualAnswer = Task4.fixString(s);

        // then
        assertThat(actualAnswer).isEqualTo("");
    }

    @Test void fixString_InputStringHasEvenLength_ReturnExpectedString() {
        String[] inputStrings = {"ba", "123456", "hTsii  s aimex dpus rtni.g"};
        String[] expectedAnswers = {"ab", "214365", "This is a mixed up string."};
        for (int i = 0; i < inputStrings.length; ++i) {
            // given
            String s = inputStrings[i];
            String expectedAnswer = expectedAnswers[i];

            // when
            String actualAnswer = Task4.fixString(s);

            // then
            assertThat(actualAnswer).isEqualTo(expectedAnswer);
        }
    }

    @Test void fixString_InputStringHasOddLength_ReturnExpectedString() {
        String[] inputStrings = {"a", "bac", "badce"};
        String[] expectedAnswers = {"a", "abc", "abcde"};
        for (int i = 0; i < inputStrings.length; ++i) {
            // given
            String s = inputStrings[i];
            String expectedAnswer = expectedAnswers[i];

            // when
            String actualAnswer = Task4.fixString(s);

            // then
            assertThat(actualAnswer).isEqualTo(expectedAnswer);
        }
    }
}
