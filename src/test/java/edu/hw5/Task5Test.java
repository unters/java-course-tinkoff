package edu.hw5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static edu.hw5.Task5.isLicencePlateValid;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task5Test {
    @Test
    void isLicencePlateValid_NullLicencePlateGiven_ThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            isLicencePlateValid(null);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"У199УА78", "А123478", "0245ОК43", "АО36578", "АН733147", "СМ655К78", "ТАО00278",
        "002CD178", "004D10877", "004T00177"})
    void isLicencePlaceValid_ValidLicencePlateGiven_ReturnTrue(String licencePlate) {
        boolean actualAnswer = isLicencePlateValid(licencePlate);
        assertThat(actualAnswer).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"123060", "BETTERCALLSAUL", "123АВЕ777", "А123ВГ77", "А123ВЕ7777", "Ё315ЖЖ77"})
    void isLicencePlaceValid_InvalidLicencePlateGiven_ReturnFalse(String licencePlate) {
        boolean actualAnswer = isLicencePlateValid(licencePlate);
        assertThat(actualAnswer).isFalse();
    }
}
