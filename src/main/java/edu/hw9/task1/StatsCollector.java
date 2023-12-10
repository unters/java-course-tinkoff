package edu.hw9.task1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import lombok.SneakyThrows;

public class StatsCollector {
    private static final int N_THREADS = 12;

    private List<Metric> metrics;
    private ReadWriteLock metricsRwLock;
    private ExecutorService executorService;

    public StatsCollector() {
        this(N_THREADS);
    }

    public StatsCollector(int nThreads) {
        metrics = new ArrayList<>();
        metricsRwLock = new ReentrantReadWriteLock();
        executorService = Executors.newFixedThreadPool(nThreads);
    }

    public void push(String metricName, double... values) {
        if (metricName == null) {
            throw new IllegalArgumentException("metricName cannot be null");
        }
        if (values == null) {
            throw new IllegalArgumentException("values cannot be null");
        }
        executorService.execute(() -> processData(metricName, values));
    }

    @SneakyThrows({ExecutionException.class, InterruptedException.class})
    public void processData(String metricName, double... values) {
        Future<Double> sumFuture = executorService.submit(() -> sum(values));
        Future<Double> averageFuture = executorService.submit(() -> average(values));
        Future<Double> maxFuture = executorService.submit(() -> max(values));
        Future<Double> minFuture = executorService.submit(() -> min(values));
        double sum = sumFuture.get();
        double average = averageFuture.get();
        double max = maxFuture.get();
        double min = minFuture.get();
        metricsRwLock.writeLock().lock();
        try {
            metrics.add(new Metric(metricName, sum, average, max, min));
        } finally {
            metricsRwLock.writeLock().unlock();
        }
    }

    public List<Metric> stats() {
        metricsRwLock.readLock().lock();
        try {
            return new ArrayList<>(metrics);
        } finally {
            metricsRwLock.readLock().unlock();
        }
    }

    private double sum(double... values) {
        return Arrays.stream(values).sum();
    }

    private double average(double... values) {
        return Arrays.stream(values).average().getAsDouble();
    }

    private double max(double... values) {
        return Arrays.stream(values).max().getAsDouble();
    }

    private double min(double... values) {
        return Arrays.stream(values).min().getAsDouble();
    }
}
