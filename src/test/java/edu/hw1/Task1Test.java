package edu.hw1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1Test {
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
        "2023", "12:34:12", "Hello World",  // No or multiple delimiters.
        "a2:20", "16:Cd", "ab:cd",          // Characters presence.
        "-12:15", "12:-15",                 // Negative values.
        "1:00", "15:9", "1:2",              // Not enough digits.
        "12:60", "45:120",                  // Too many seconds.
        "10000000000000000:00"              // Video is older than the universe.
    })
    void convertTimestampToSeconds_WrongInputFormat_ReturnMinusOne(String timestamp) {
        long actualAnswer = Task1.convertTimestampToSeconds(timestamp);
        assertThat(actualAnswer).isEqualTo(-1L);
    }

    @ParameterizedTest
    @CsvSource({"00:00, 0", "00:30, 30", "01:30, 90", "10:00, 600"})
    void convertTimestampToSeconds_InputIsValid_ReturnExpectedValue(String timestamp, long expectedAnswer) {
        long actualAnswer = Task1.convertTimestampToSeconds(timestamp);
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
