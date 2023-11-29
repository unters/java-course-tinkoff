package edu.hw7.task1;

import lombok.Getter;

@Getter
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

    private int value;
}
