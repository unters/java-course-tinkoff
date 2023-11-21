package edu.hw7.task1;

public class Counter {
    public Counter(int value) {
        this.value = value;
    }

    public synchronized void increment() {
        ++value;
    }

    public synchronized void decrement() {
        --value;
    }

    public int getValue() {
        return value;
    }

    private int value;
}
