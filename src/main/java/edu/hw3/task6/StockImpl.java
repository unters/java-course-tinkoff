package edu.hw3.task6;

import org.jetbrains.annotations.NotNull;

public record StockImpl(int cost) implements Stock {
    public StockImpl {
        if (cost <= 0) {
            throw new IllegalArgumentException("Cost must be positive: " + cost);
        }
    }

    @Override
    public int compareTo(@NotNull Stock otherStock) {
        return Integer.compare(this.cost, otherStock.cost());
    }

    @Override
    public String toString() {
        return "Stock: " + cost;
    }
}
