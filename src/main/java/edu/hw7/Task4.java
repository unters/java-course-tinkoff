package edu.hw7;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;
import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static java.lang.Math.pow;

/* On my laptop (6 physical cores, 12 logical cores, 2.6ghz) it takes 3.1 seconds to approximate Pi using 12 threads
 * vs. 54.08 seconds using 1 thread (in both cases 1 million dots get "thrown").  */
@UtilityClass
public class Task4 {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String INVALID_DOTS_TOTAL_COUNT_MESSAGE = "dotsTotalCount must be positive";

    /* Or should I have suppressed those magic numbers?  */
    private static final double PI_DIVISOR = 4.0;
    private static final double X_OFFSET = 0.5;
    private static final double Y_OFFSET = 0.5;
    private static final double RADIUS = 0.5;
    private static final double RADIUS_SQUARED = pow(RADIUS, 2);

    /* I've left main method for your convenience.  */
//    public static void main(String[] args) {
//        long dotsTotalCount = 1_000_000_000;
//        int threadsCount = 12;
//
//        long startTime = System.nanoTime();
////        double pi = approximatePiByMonteCarloMethodInASingleThread(dotsTotalCount);
//        double pi = approximatePiByMonteCarloMethodInMultipleThreads(dotsTotalCount, threadsCount);
//        long finishTime = System.nanoTime();
//
//        double elapsedTime = ((double) finishTime - startTime) / 1_000_000_000;
//        LOGGER.info(pi + " in " + elapsedTime + " seconds");
//    }

    public static double approximatePiByMonteCarloMethodInASingleThread(long dotsTotalCount) {
        if (dotsTotalCount < 1L) {
            throw new IllegalArgumentException(INVALID_DOTS_TOTAL_COUNT_MESSAGE);
        }

        long dotsInCircleCount = 0;
        RandomGenerator randomGenerator = new Random();
        for (long i = 0; i < dotsTotalCount; ++i) {
            double y = randomGenerator.nextDouble();
            double x = randomGenerator.nextDouble();
            if (pow((x - X_OFFSET), 2) + pow((y - Y_OFFSET), 2) < RADIUS_SQUARED) {
                ++dotsInCircleCount;
            }
        }

        return PI_DIVISOR * ((double) dotsInCircleCount / dotsTotalCount);
    }

    public static double approximatePiByMonteCarloMethodInMultipleThreads(long dotsTotalCount, int threadsCount) {
        if (dotsTotalCount < 1L) {
            throw new IllegalArgumentException(INVALID_DOTS_TOTAL_COUNT_MESSAGE);
        }

        if (threadsCount < 1) {
            throw new IllegalArgumentException("threadsCount must be positive");
        }

        if (dotsTotalCount < threadsCount) {
            throw new IllegalArgumentException("dotsTotalCount must be greater or equals to threadsCount");
        }

        final long dotsPerThread = dotsTotalCount / threadsCount;
        List<MonteCarloPiApproximationRunnable> runnables = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadsCount; ++i) {
            MonteCarloPiApproximationRunnable runnable = new MonteCarloPiApproximationRunnable(dotsPerThread);
            runnables.add(runnable);
            threads.add(new Thread(runnable));
        }

        for (int i = 0; i < threadsCount; ++i) {
            threads.get(i).start();
        }

        for (int i = 0; i < threadsCount; ++i) {
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                LOGGER.info(e.getMessage());
            }
        }

        long dotsInCircleCount = 0;
        for (MonteCarloPiApproximationRunnable runnable : runnables) {
            dotsInCircleCount += runnable.dotsInCircleCount;
        }

        return PI_DIVISOR * ((double) dotsInCircleCount / dotsTotalCount);
    }

    private static class MonteCarloPiApproximationRunnable implements Runnable {
        MonteCarloPiApproximationRunnable(long dotsTotalCount) {
            this.dotsTotalCount = dotsTotalCount;
        }

        @Override
        public void run() {
            for (long i = 0; i < dotsTotalCount; ++i) {
                double y = ThreadLocalRandom.current().nextDouble();
                double x = ThreadLocalRandom.current().nextDouble();
                if (pow((x - X_OFFSET), 2) + pow((y - Y_OFFSET), 2) < RADIUS_SQUARED) {
                    ++dotsInCircleCount;
                }
            }
        }

        private final long dotsTotalCount;
        private long dotsInCircleCount = 0;
    }
}
