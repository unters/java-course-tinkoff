package edu.hw5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static edu.hw5.Task4.isPasswordValid;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task4Test {
    @Test
    void isPasswordValid_NullStringGiven_ThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            isPasswordValid(null);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"Tilda~", "B2d_!opa", "@yaru", "as1#fd", "20$is20bucks", "7011%", "n^ot", "amp&3rsand",
        "5t*aR", "am|3_cl", "fh&%Syt#pao!"})
    void isPasswordValid_ValidPasswordGiven_ReturnTrue(String password) {
        boolean actualAnswer = isPasswordValid(password);
        assertThat(actualAnswer).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"qwerty123", "123000", "toor", "super_strong_79", "mark2@gmail.com"})
    void isPasswordValid_InvalidPasswordGiven_ReturnFalse(String password) {
        boolean actualAnswer = isPasswordValid(password);
        assertThat(actualAnswer).isFalse();
    }
}
