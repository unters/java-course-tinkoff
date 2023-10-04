package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {
    @Test void countDigits_InputIsValid_ReturnExpectedResult() {
        // given
        int[] inputValues = {0, 1, 91, 134, 4926, 181900031, -1, -31, -89007};

        int[] expectedValues = {1, 1, 2, 3, 4, 9, 1, 2, 5};

        for (int i = 0; i < inputValues.length; ++i) {
            // when
            int actualAnswer = Task2.countDigits(inputValues[i]);
            int expectedAnswer = expectedValues[i];

            // then
            assertThat(actualAnswer).isEqualTo(expectedAnswer);
        }
    }
}
