package edu.hw3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static edu.hw3.Task1.encryptUsingAtbash;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task1Test {
    @ParameterizedTest
    @CsvSource({"aBcD, zYxW", "12_3, 12_3", "абв, абв", "a1_Dд+, z1_Wд+"})
    void encryptUsingAtbash_NonNullStringPassed_ReturnExpectedAnswer(String s, String expectedAnswer) {
        String actualAnswer = encryptUsingAtbash(s);
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }

    @Test
    void encryptUsingAtbash_NullArgumentPassed_ThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            encryptUsingAtbash(null);
        });
    }
}
