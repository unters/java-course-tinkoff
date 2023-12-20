package edu.hw8;

import edu.hw8.task2.FixedThreadPool;
import edu.hw8.task2.RunnableFuture;
import edu.hw8.task2.ThreadPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class FixedThreadPoolTest {
    private static final Logger LOGGER = LogManager.getLogger();

    @Test
    void execute_CalculateMultipleFibonacciNumbers_NoExceptionIsThrown() {
        // given
        int nThreads = 6;
        int[] fibonacciNumberIndices =
            new int[] {12312, 32131, 42633, 29235, 57231, 71634, 15664, 24912, 29913, 123929, 11291};

        assertDoesNotThrow(() -> {
            List<RunnableFuture> runnableFutures = new ArrayList<>();
            try (ThreadPool threadPool = FixedThreadPool.create(nThreads)) {
                for (int i = 0; i < fibonacciNumberIndices.length; ++i) {
                    int fibonacciNumberIndex = fibonacciNumberIndices[i];
                    runnableFutures.add(threadPool.execute(() -> calculateNthFibonacciNumber(fibonacciNumberIndex)));
                }

                for (int i = 0; i < runnableFutures.size(); ++i) {
                    while (!runnableFutures.get(i).isDone()) {
                    }
                }
            }
        });
    }

    private static void calculateNthFibonacciNumber(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be non-negative");
        }

        if (n == 0) {
            LOGGER.info("0'th fibonacci number is 0");
        }

        BigInteger a = BigInteger.valueOf(0);
        BigInteger b = BigInteger.valueOf(1);
        for (int j = 2; j <= n; ++j) {
            BigInteger c = a.add(b);
            a = b;
            b = c;
        }
        LOGGER.info("%d'th fibonacci number is %s".formatted(n, b.toString()));
    }
}
