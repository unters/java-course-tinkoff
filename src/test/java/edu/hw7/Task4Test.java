package edu.hw7;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static edu.hw7.Task4.approximatePiByMonteCarloMethodInASingleThread;
import static edu.hw7.Task4.approximatePiByMonteCarloMethodInMultipleThreads;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task4Test {
    @ParameterizedTest
    @ValueSource(longs = {-100L, -1L, 0L})
    void approximatePiByMonteCarloMethodInASingleThread_InvalidArgumentGiven_IllegalArgumentExceptionIsThrown(
        long dotsTotalCount
    ) {
        assertThrows(IllegalArgumentException.class, () -> {
            approximatePiByMonteCarloMethodInASingleThread(dotsTotalCount);
        });
    }

    @ParameterizedTest
    @CsvSource({"-1000, 1", "1000, -1", "0, 0", "-100, -12", "6, 12"})
    void approximatePiByMonteCarloMethodInMultipleThreads_InvalidArgumentsGiven_IllegalArgumentExceptionIsThrown(
        long dotsTotalCount,
        int threads
    ) {
        assertThrows(IllegalArgumentException.class, () -> {
            approximatePiByMonteCarloMethodInMultipleThreads(dotsTotalCount, threads);
        });
    }

    @ParameterizedTest
    @ValueSource(longs = {100L, 1000L, 10000L})
    void approximatePiByMonteCarloMethodInASingleThread_ValidArgumentGiven_NoExceptionIsThrown(
        long dotsTotalCount
    ) {
        assertDoesNotThrow(() -> {
            approximatePiByMonteCarloMethodInASingleThread(dotsTotalCount);
        });
    }

    @ParameterizedTest
    @CsvSource({"100, 1", "1000, 2", "1000000, 12"})
    void approximatePiByMonteCarloMethodInMultipleThreads_ValidArgumentsGiven_NoExceptionIsThrown(
        long dotsTotalCount,
        int threads
    ) {
        assertDoesNotThrow(() -> {
            approximatePiByMonteCarloMethodInMultipleThreads(dotsTotalCount, threads);
        });
    }
}
