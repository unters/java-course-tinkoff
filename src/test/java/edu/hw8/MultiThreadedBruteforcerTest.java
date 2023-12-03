package edu.hw8;

import edu.hw8.task3.MultiThreadedPasswordBruteforcer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MultiThreadedBruteforcerTest {
    @ParameterizedTest
    @CsvSource({"-1, 1", "0, 1", "1, 1", "10, -10"})
    void bruteforceMinMaxLengths_NullPasswordHashsGiven_ThrowIllegalArgumentsException(int minLength, int maxLength) {
        assertThrows(IllegalArgumentException.class, () -> {
            MultiThreadedPasswordBruteforcer.bruteforce(null, minLength, maxLength);
        });
    }

    @ParameterizedTest
    @CsvSource({"e10adc3949ba59abbe56e057f20f883e, -1, 10", "e10adc3949ba59abbe56e057f20f883e, 0, 10",
        "e10adc3949ba59abbe56e057f20f883e, 1, 1", "e10adc3949ba59abbe56e057f20f883e, 1, -1"})
    void bruteforceMinMaxLengths_InvalidLengthsGiven_ThrowIllegalArgumentsException(
        String passwordHash,
        int minLength,
        int maxLength
    ) {
        assertThrows(IllegalArgumentException.class, () -> {
            MultiThreadedPasswordBruteforcer.bruteforce(passwordHash, minLength, maxLength);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1})
    void bruteforceExpectedLength_NullPasswordHashsGiven_ThrowIllegalArgumentsException(int expectedLength) {
        assertThrows(IllegalArgumentException.class, () -> {
            MultiThreadedPasswordBruteforcer.bruteforce(null, expectedLength);
        });
    }

    @ParameterizedTest
    @CsvSource({"e10adc3949ba59abbe56e057f20f883e, -10", "e10adc3949ba59abbe56e057f20f883e, -1",
        "e10adc3949ba59abbe56e057f20f883e, 0"})
    void bruteforceExpectedLength_InvalidLengthGiven_ThrowIllegalArgumentsException(
        String passwordHash,
        int expectedLength
    ) {
        assertThrows(IllegalArgumentException.class, () -> {
            MultiThreadedPasswordBruteforcer.bruteforce(passwordHash, expectedLength);
        });
    }

    @ParameterizedTest
    @CsvSource({"072623e6ba866a6db73228be0af0bf45, 4, ab10", "962012d09b8170d912f0669f6d7d9d07, 4, qwer",
        "f3abb86bd34cf4d52698f14c0da1dc60, 3, zzz"})
    void bruteforce_ValidAssumptionAboutLengthHasBeenMade_ReturnedListContainsActualPassword(
        String passwordHash,
        int expectedLength,
        String actualPassword
    ) {
        // when
        List<String> potentialPasswords = MultiThreadedPasswordBruteforcer.bruteforce(passwordHash, expectedLength);

        // then
        assertThat(potentialPasswords.contains(actualPassword)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"072623e6ba866a6db73228be0af0bf45, 3, 5, ab10", "962012d09b8170d912f0669f6d7d9d07, 3, 5, qwer",
        "f3abb86bd34cf4d52698f14c0da1dc60, 3, 5, zzz"})
    void bruteforce_ValidAssumptionAboutLengthRangeHasBeenMade_ReturnedListContainsActualPassword(
        String passwordHash,
        int minLength,
        int maxLength,
        String actualPassword
    ) {
        // when
        List<String> potentialPasswords =
            MultiThreadedPasswordBruteforcer.bruteforce(passwordHash, minLength, maxLength);

        // then
        assertThat(potentialPasswords.contains(actualPassword)).isTrue();
    }
}
