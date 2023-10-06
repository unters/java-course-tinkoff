package edu.hw1;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task7Test {
    private static final int L_BIT = 0b10000000000000000000000000000000;
    private static final int R_BIT = 0b00000000000000000000000000000001;

    @Test void rotateLeft_ShiftIsNegative_ReturnResultOfRightRotation() {
        int[] inputN = {L_BIT, 557056, R_BIT};
        int[] inputShift = {-1, -40, -7};
        int[] expectedAnswers = {1073741824, 2176, 33554432};
        for (int i = 0; i < inputN.length; ++i) {
            // given
            int n = inputN[i];
            int shift = inputShift[i];
            int expectedAnswer = expectedAnswers[i];

            // when
            int actualAnswer = Task7.rotateLeft(n, shift);

            // then
            assertThat(actualAnswer).isEqualTo(expectedAnswer);
        }
    }

    @Test void rotateRight_ShiftIsNegative_ReturnResultOfLeftRotation() {
        int[] inputN = {1073741824, 2176, 33554432};
        int[] inputShift = {-1, -40, -7};
        int[] expectedAnswers = {L_BIT, 557056, R_BIT};
        for (int i = 0; i < inputN.length; ++i) {
            // given
            int n = inputN[i];
            int shift = inputShift[i];
            int expectedAnswer = expectedAnswers[i];

            // when
            int actualAnswer = Task7.rotateRight(n, shift);

            // then
            assertThat(actualAnswer).isEqualTo(expectedAnswer);
        }
    }

    @Test void rotateLeft_ShiftIsNonNegative_ReturnExpectedAnswer() {
        int[] inputN = {1073741824, 2176, 33554432};
        int[] inputShift = {1, 40, 7};
        int[] expectedAnswers = {L_BIT, 557056, R_BIT};
        for (int i = 0; i < inputN.length; ++i) {
            // given
            int n = inputN[i];
            int shift = inputShift[i];
            int expectedAnswer = expectedAnswers[i];

            // when
            int actualAnswer = Task7.rotateLeft(n, shift);

            // then
            assertThat(actualAnswer).isEqualTo(expectedAnswer);
        }
    }

    @Test void rotateRight_ShiftIsNonNegative_ReturnExpectedAnswer() {
        int[] inputN = {L_BIT, 557056, R_BIT};
        int[] inputShift = {1, 40, 7};
        int[] expectedAnswers = {1073741824, 2176, 33554432};
        for (int i = 0; i < inputN.length; ++i) {
            // given
            int n = inputN[i];
            int shift = inputShift[i];
            int expectedAnswer = expectedAnswers[i];

            // when
            int actualAnswer = Task7.rotateRight(n, shift);

            // then
            assertThat(actualAnswer).isEqualTo(expectedAnswer);
        }
    }
}
