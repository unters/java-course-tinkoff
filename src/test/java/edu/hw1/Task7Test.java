package edu.hw1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task7Test {
    @ParameterizedTest
    @CsvSource({"-2147483648, -1, 1073741824", "557056, -40, 2176", "1, -7, 33554432"})
    void rotateLeft_ShiftIsNegative_ReturnResultOfRightRotation(int n, int shift, int expectedAnswer) {
        int actualAnswer = Task7.rotateLeft(n, shift);
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }

    @ParameterizedTest
    @CsvSource({"1073741824, -1, -2147483648", "2176, -40, 557056", "33554432, -7, 1"})
    void rotateRight_ShiftIsNegative_ReturnResultOfLeftRotation(int n, int shift, int expectedAnswer) {
        int actualAnswer = Task7.rotateRight(n, shift);
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }

    @ParameterizedTest
    @CsvSource({"1073741824, 1, -2147483648", "2176, 40, 557056", "33554432, 7, 1"})
    void rotateLeft_ShiftIsNonNegative_ReturnExpectedAnswer(int n, int shift, int expectedAnswer) {
        int actualAnswer = Task7.rotateLeft(n, shift);
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }

    @ParameterizedTest
    @CsvSource({"-2147483648, 1, 1073741824", "557056, 40, 2176", "1, 7, 33554432"})
    void rotateRight_ShiftIsNonNegative_ReturnExpectedAnswer(int n, int shift, int expectedAnswer) {
        int actualAnswer = Task7.rotateRight(n, shift);
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
