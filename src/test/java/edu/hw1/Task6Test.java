package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task6Test {
    @Test void countK_InputValueIsLessOrEqualThen1000_ReturnMinusOne() {
        // given
        long[] inputValues = {-12345, -1234, -1001, -1000, -999, 0, 999, 1000};
        for (long value : inputValues) {
            // when
            int actualAnswer = Task6.countK(value);

            // then
            assertThat(actualAnswer).isEqualTo(-1);
        }
    }

    @Test void countK_InputIsValid_ReturnExpectedValue() {
        long[] inputValues = {6621, 6554, 1234};
        int[] expectedAnswers = {5, 4, 3};
        for (int i = 0; i < inputValues.length; ++i) {
            // given
            long value = inputValues[i];

            // when
            int actualAnswer = Task6.countK(value);

            // then
            int expectedAnswer = expectedAnswers[i];
            assertThat(actualAnswer).isEqualTo(expectedAnswer);
        }
    }
}
