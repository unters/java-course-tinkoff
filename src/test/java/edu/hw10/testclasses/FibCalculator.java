package edu.hw10.testclasses;

import edu.hw10.task2.Cache;

public interface FibCalculator {
    @Cache(persist = true)
    int calculate(int n);
}
