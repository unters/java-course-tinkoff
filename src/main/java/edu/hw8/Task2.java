package edu.hw8;

import edu.hw8.task2.FixedThreadPool;
import edu.hw8.task2.RunnableFuture;
import edu.hw8.task2.ThreadPool;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@UtilityClass
@SuppressWarnings("UncommentedMain")
public class Task2 {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final int N_THREADS = 12;

    @SneakyThrows(Exception.class)
    @SuppressWarnings("MagicNumber")
    public static void main(String[] args) {
        int[] fibonacciNumberIndices =
            new int[] {12312, 32131, 42633, 29235, 57231, 71634, 15664, 24912, 29913, 123929, 11291};

        long start = System.nanoTime();
        calculateInOneThread(fibonacciNumberIndices);
        long stop = System.nanoTime();
        double singleThreadElapsedTime = ((double) stop - start) / 1_000_000_000;

        start = System.nanoTime();
        calculateInMultipleThreads(fibonacciNumberIndices, N_THREADS);
        stop = System.nanoTime();
        double nThreadsElapsedTime = ((double) stop - start) / 1_000_000_000;

        LOGGER.warn("Single Thread (%.2f seconds) vs. n threads (%.2f seconds)".formatted(
            singleThreadElapsedTime,
            nThreadsElapsedTime
        ));
    }

    private static void calculateInOneThread(int[] fibonacciNumberIndices) {
        for (int index : fibonacciNumberIndices) {
            calculateNthFibonacciNumber(index);
        }
    }

    @SneakyThrows(Exception.class)
    private static void calculateInMultipleThreads(int[] fibonacciNumberIndices, int nThreads) {
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
    }

    private static void calculateNthFibonacciNumber(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be non-negative");
        }

        if (n == 0) {
            LOGGER.info("%0'th fibonacci number is 0");
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
