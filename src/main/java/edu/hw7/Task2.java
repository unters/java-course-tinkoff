package edu.hw7;

import java.math.BigInteger;
import java.util.stream.IntStream;

/* I completely forgot that factorial can be calculated in parallel: https://habr.com/ru/articles/255761/  */
public class Task2 {
    public static BigInteger calculateFactorialInParallel(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be non-negative");
        }

        if (n < 2) {
            return BigInteger.valueOf(1);
        }

        return IntStream.rangeClosed(2, n)
            .parallel()
            .mapToObj(BigInteger::valueOf)
            .reduce(BigInteger::multiply)
            .get();
    }

    private Task2() {
    }
}
