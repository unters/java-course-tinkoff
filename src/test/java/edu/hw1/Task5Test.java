package edu.hw1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task5Test {
    @ParameterizedTest
    @ValueSource(ints = {-1, -50, -123, -5115})
    void isPalindromeDescendant_InputValueIsNegative_ThrowIllegalArgumentException(int value) {
        assertThrows(IllegalArgumentException.class, () -> {
            Task5.isPalindromeDescendant(value);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 252, 6336, 1905091})
    void isPalindromeDescendant_InputValueIsPalindrome_ReturnTrue(int value) {
        boolean actualAnswer = Task5.isPalindromeDescendant(value);
        assertThat(actualAnswer).isEqualTo(true);
    }

    @ParameterizedTest
    @ValueSource(ints = {11211230, 13001120, 23336014})
    void isPalindromeDescendant_InputValueHasPalindromeDescendant_ReturnTrue(int value) {
        boolean actualAnswer = Task5.isPalindromeDescendant(value);
        assertThat(actualAnswer).isEqualTo(true);
    }

    @ParameterizedTest
    @ValueSource(ints = {13, 147, 1992})
    void isPalindromeDescendant_InputValueIsNotPalindromeAndHasNoPalindromeDescendants_ReturnFalse(int value) {
        boolean actualAnswer = Task5.isPalindromeDescendant(value);
        assertThat(actualAnswer).isEqualTo(false);
    }
}
