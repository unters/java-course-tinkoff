package edu.hw8.task2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FixedThreadPool implements ThreadPool {
    private static final Logger LOGGER = LogManager.getLogger();

    private final int nThreads;
    private final Thread[] workers;
    private final BlockingQueue<Pair> queue = new LinkedBlockingQueue<>();

    public static ThreadPool create(int nThreads) {
        if (nThreads < 1) {
            throw new IllegalArgumentException("nThreads must be positive");
        }

        return new FixedThreadPool(nThreads);
    }

    @Override
    public RunnableFuture execute(Runnable runnable) {
        RunnableFuture future = new RunnableFuture();
        synchronized (queue) {
            queue.add(new Pair(future, runnable));
            LOGGER.info("Worker: runnable added to queue, current size: " + queue.size());
            queue.notify();
        }
        return future;
    }

    @Override
    public void close() throws Exception {
        for (Thread thread : workers) {
            thread.interrupt();
        }
    }

    private FixedThreadPool(int nThreads) {
        this.nThreads = nThreads;
        workers = new Worker[nThreads];
        for (int i = 0; i < nThreads; ++i) {
            workers[i] = new Worker();
            workers[i].start();
        }
    }

    private record Pair(RunnableFuture future, Runnable runnable) {  }

    private class Worker extends Thread {
        public void run() {
            RunnableFuture future;
            Runnable runnable;

            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty() && !isInterrupted()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            LOGGER.info("Worker: interruption signal received");
                            return;
                        }
                    }

                    Pair p = queue.poll();
                    future = p.future;
                    runnable = p.runnable;
                    LOGGER.info("Worker: task polled, current queue size " + queue.size());
                }

                try {
                    runnable.run();
                    future.setDone();
                } catch (RuntimeException e) {
                    LOGGER.error("Worker: exception occurred while running the task: " + e.getMessage());
                }
            }
        }
    }
}
