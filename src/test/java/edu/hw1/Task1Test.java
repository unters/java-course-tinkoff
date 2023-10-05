package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1Test {
    @Test void minutesToSeconds_InputContainsZeroOrMoreThanOneDelimiter_ReturnMinusOne() {
        // given
        String[] inputStrings = {"124356", "123321:59:01"};

        BigInteger expectedAnswer = new BigInteger("-1");
        for (String s : inputStrings) {
            // when
            BigInteger actualAnswer = Task1.minutesToSeconds(s);

            // then
            assertThat(actualAnswer).isEqualTo(expectedAnswer);
        }
    }

    @Test void minutesToSeconds_InputContainsNonDigitCharactersExceptForColon_ReturnMinusOne() {
        // given
        String[] inputStrings = {"ab:20", "01:al", "a9:b8", "ab:cd", "Hello World!"};

        BigInteger expectedAnswer = new BigInteger("-1");
        for (String s : inputStrings) {
            // when
            BigInteger actualAnswer = Task1.minutesToSeconds(s);

            // then
            assertThat(actualAnswer).isEqualTo(expectedAnswer);
        }
    }

    @Test void minutesToSeconds_InputContainsLessThanTwoCharactersBeforeTheDelimiter_ReturnMinusOne() {
        // given
        String[] inputStrings = {"1:00", "7:32"};

        BigInteger expectedAnswer = new BigInteger("-1");
        for (String s : inputStrings) {
            // when
            BigInteger actualAnswer = Task1.minutesToSeconds(s);

            // then
            assertThat(actualAnswer).isEqualTo(expectedAnswer);
        }
    }

    @Test void minutesToSeconds_InputContainsNotTwoCharactersAfterColon_ReturnMinusOne() {
        // given
        String[] inputStrings = {"123:0", "246:9", "789:010", "456:101"};

        BigInteger expectedAnswer = new BigInteger("-1");
        for (String s : inputStrings) {
            // when
            BigInteger actualAnswer = Task1.minutesToSeconds(s);

            // then
            assertThat(actualAnswer).isEqualTo(expectedAnswer);
        }
    }

    @Test void minutesToSeconds_InputContainsNoMinutesOrSeconds_ReturnMinusOne() {
        // given
        String[] inputStrings = {":00", "12:", ":"};

        BigInteger expectedAnswer = new BigInteger("-1");
        for (String s : inputStrings) {
            // when
            BigInteger actualAnswer = Task1.minutesToSeconds(s);

            // then
            assertThat(actualAnswer).isEqualTo(expectedAnswer);
        }
    }

    @Test void minutesToSeconds_SecondsValueInInputIsGreaterOrEqualThan60_ReturnMinusOne() {
        // given
        String[] inputStrings = {"123:60", "123:123"};

        BigInteger expectedAnswer = new BigInteger("-1");
        for (String s : inputStrings) {
            // when
            BigInteger actualAnswer = Task1.minutesToSeconds(s);

            // then
            assertThat(actualAnswer).isEqualTo(expectedAnswer);
        }
    }

    @Test void minutesToSeconds_InputIsValid_ReturnExpectedValue() {
        // given
        String[] inputStrings = {"00:00", "00:30", "01:30", "10:00", "1000000000000000000000000:24"};

        String[] expectedAnswers = {"0", "30", "90", "600", "60000000000000000000000024"};
        for (int i = 0; i < inputStrings.length; ++i) {
            // when
            BigInteger actualAnswer = Task1.minutesToSeconds(inputStrings[i]);
            BigInteger expectedAnswer = new BigInteger(expectedAnswers[i]);

            // then
            assertThat(actualAnswer).isEqualTo(expectedAnswer);
        }
    }
}
