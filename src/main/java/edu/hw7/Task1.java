package edu.hw7;

import edu.hw7.task1.Counter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Task1 {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Simultaneously increment and decrement given counter t times in 2 parallel threads.
     *
     * @param counter Counter to execute the procedure on.
     * @param t Number of times the procedure will be executed.
     */
    public static void executeProcedure(Counter counter, int t) {
        if (counter == null) {
            throw new IllegalArgumentException("counter cannot be null");
        }

        if (t <= 0) {
            throw new IllegalArgumentException("t must be positive");
        }

        Thread incrementingThread = new Thread(() -> {
            for (int i = 0; i < t; ++i) {
                counter.increment();
            }
        });

        Thread decrementingThread = new Thread(() -> {
            for (int i = 0; i < t; ++i) {
                counter.decrement();
            }
        });

        incrementingThread.start();
        decrementingThread.start();
        try {
            incrementingThread.join();
            decrementingThread.join();
        } catch (InterruptedException e) {
            LOGGER.info(e.getMessage());
        }
    }

    private Task1() {
    }
}
