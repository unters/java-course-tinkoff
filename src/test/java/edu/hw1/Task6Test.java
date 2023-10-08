package edu.hw1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task6Test {
    @ParameterizedTest
    @ValueSource(shorts = {-12_345, -1234, -1001, -1000, -999, 0, 999, 10_000})
    void countIterationsInKaprekarsRoutine_InputValueIsNotExactlyFourDigitsLong_ReturnMinusOne(short value) {
        int actualAnswer = Task6.countIterationsInKaprekarsRoutine(value);
        assertThat(actualAnswer).isEqualTo(-1);
    }

    @ParameterizedTest
    @CsvSource({"6621, 5", "6554, 4", "1234, 3", "9998, 5", "6174, 1"})
    void countIterationsInKaprekarsRoutine_InputIsValid_ReturnExpectedValue(short value, int expectedAnswer) {
        int actualAnswer = Task6.countIterationsInKaprekarsRoutine(value);
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
