package edu.hw1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task4Test {
    @ParameterizedTest
    @NullSource
    void fixString_InputStringIsNull_ThrowException(String s) {
        assertThrows(IllegalArgumentException.class, () -> {
            Task4.fixString(s);
        });
    }

    @ParameterizedTest
    @EmptySource
    void fixString_InputStringIsEmpty_ReturnEmptyString(String s) {
        String actualAnswer = Task4.fixString(s);
        assertThat(actualAnswer).isEqualTo("");
    }

    @ParameterizedTest
    @CsvSource({"ba, ab", "123456, 214365", "hTsii  s aimex dpus rtni.g, This is a mixed up string."})
    void fixString_InputStringHasEvenLength_ReturnExpectedString(String s, String expectedAnswer) {
        String actualAnswer = Task4.fixString(s);
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }

    @ParameterizedTest
    @CsvSource({"a, a", "bac, abc", "badce, abcde"})
    void fixString_InputStringHasOddLength_ReturnExpectedString(String s, String expectedAnswer) {
        String actualAnswer = Task4.fixString(s);
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
