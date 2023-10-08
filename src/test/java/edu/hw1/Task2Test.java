package edu.hw1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {
    @ParameterizedTest
    @CsvSource({"0, 1", "1, 1", "91, 2", "134, 3", "4926, 4", "181_900_031, 9", "-1, 1", "-31, 2", "-89007, 5"})
    void countDigits_InputIsValid_ReturnExpectedResult(int value, int expectedAnswer) {
        int actualAnswer = Task2.countDigits(value);
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
