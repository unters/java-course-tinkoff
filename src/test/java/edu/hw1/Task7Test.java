package edu.hw1;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task7Test {
    @Test void rotateLeft_ShiftIsNegative_ReturnResultOfRightRotation() {
        byte[] inputN = {(byte) 0b10000000, (byte) 0b10000000, (byte) 0b00000001, (byte) 0b00001001};
        int[] inputShift = {-1, -8, -7, -3};
        byte[] expectedAnswers = {(byte) 0b01000000, (byte) 0b10000000, (byte) 0b00000010, (byte) 0b00100001};
        for (int i = 0; i < inputN.length; ++i) {
            // given
            byte n = inputN[i];
            int shift = inputShift[i];
            byte expectedAnswer = expectedAnswers[i];

            // when
            byte actualAnswer = Task7.rotateLeft(n, shift);

            // then
            assertThat(actualAnswer).isEqualTo(expectedAnswer);
        }
    }

    @Test void rotateRight_ShiftIsNegative_ReturnResultOfLeftRotation() {
        byte[] inputN = {(byte) 0b10000000, (byte) 0b10000000, (byte) 0b00000001, (byte) 0b00001001};
        int[] inputShift = {-1, -8, -7, -3};
        byte[] expectedAnswers = {(byte) 0b00000001, (byte) 0b10000000, (byte) 0b10000000, (byte) 0b01001000};
        for (int i = 0; i < inputN.length; ++i) {
            // given
            byte n = inputN[i];
            int shift = inputShift[i];
            byte expectedAnswer = expectedAnswers[i];

            // when
            byte actualAnswer = Task7.rotateRight(n, shift);

            // then
            assertThat(actualAnswer).isEqualTo(expectedAnswer);
        }
    }

    @Test void rotateLeft_ShiftIsNonNegative_ReturnExpectedAnswer() {
        byte[] inputN = {(byte) 0b10000000, (byte) 0b10000000, (byte) 0b00000001, (byte) 0b00001001, 0};
        int[] inputShift = {1, 8, 7, 3, 5};
        byte[] expectedAnswers = {(byte) 0b00000001, (byte) 0b10000000, (byte) 0b10000000, (byte) 0b01001000, 0};
        for (int i = 0; i < inputN.length; ++i) {
            // given
            byte n = inputN[i];
            int shift = inputShift[i];
            byte expectedAnswer = expectedAnswers[i];

            // when
            byte actualAnswer = Task7.rotateLeft(n, shift);

            // then
            assertThat(actualAnswer).isEqualTo(expectedAnswer);
        }
    }

    @Test void rotateRight_ShiftIsNonNegative_ReturnExpectedAnswer() {
        byte[] inputN = {(byte) 0b00000001, (byte) 0b10000000, (byte) 0b10000000, (byte) 0b01001000, 0};
        int[] inputShift = {1, 8, 7, 3, 5};
        byte[] expectedAnswers = {(byte) 0b10000000, (byte) 0b10000000, (byte) 0b00000001, (byte) 0b00001001, 0};
        for (int i = 0; i < inputN.length; ++i) {
            // given
            byte n = inputN[i];
            int shift = inputShift[i];
            byte expectedAnswer = expectedAnswers[i];

            // when
            byte actualAnswer = Task7.rotateRight(n, shift);

            // then
            assertThat(actualAnswer).isEqualTo(expectedAnswer);
        }
    }
}
