package edu.hw1;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task5Test {
    @Test void isPalindromeDescendant_InputValueIsNegative_ThrowIllegalArgumentException() {
        // given
        int value = -1;

        // when
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            Task5.isPalindromeDescendant(value);
        });

        // then
        assertThat(e.getMessage()).contains("cannot be negative");
    }

    @Test void isPalindromeDescendant_InputValueIsPalindrome_ReturnTrue() {
        // given
        int[] inputValues = {1, 252, 6336, 1905091};

        for (int value : inputValues) {
            //when
            boolean actualAnswer = Task5.isPalindromeDescendant(value);

            // then
            assertThat(actualAnswer).isEqualTo(true);
        }
    }

    @Test void isPalindromeDescendant_InputValueHasPalindromeDescendant_ReturnTrue() {
        // given
        int[] inputValues = {11211230, 13001120, 23336014};

        for (int value : inputValues) {
            //when
            boolean actualAnswer = Task5.isPalindromeDescendant(value);

            // then
            assertThat(actualAnswer).isEqualTo(true);
        }
    }

    @Test void isPalindromeDescendant_InputValueIsNotPalindromeAndHasNoPalindromeDescendants_ReturnFalse() {
        // given
        int[] inputValues = {13, 147, 1992};

        for (int value : inputValues) {
            //when
            boolean actualAnswer = Task5.isPalindromeDescendant(value);

            // then
            assertThat(actualAnswer).isEqualTo(false);
        }
    }
}
