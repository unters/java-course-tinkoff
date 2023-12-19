package edu.hw10.testclasses;

public class SimpleFibCalculator implements FibCalculator {
    @Override
    public int calculate(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }

        int a = 1;
        int b = 1;
        for (int i = 3; i <= n; ++i) {
            int c = b;
            b = a + b;
            a = c;
        }

        return b;
    }
}
