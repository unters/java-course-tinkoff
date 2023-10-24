package edu.hw3.task6;

import java.util.Collections;
import java.util.PriorityQueue;
import org.jetbrains.annotations.NotNull;

public class StockMarketImpl implements StockMarket {
    private final PriorityQueue<Stock> priorityQueue = new PriorityQueue<>(Collections.reverseOrder());

    @Override
    public void add(@NotNull Stock stock) {
        priorityQueue.add(stock);
    }

    @Override
    public void remove(Stock stock) {
        if (!priorityQueue.isEmpty()) {
            priorityQueue.remove(stock);
        }
    }

    @Override
    public Stock getMostValuableStock() {
        if (priorityQueue.isEmpty()) {
            return null;
        }

        return priorityQueue.peek();
    }
}
