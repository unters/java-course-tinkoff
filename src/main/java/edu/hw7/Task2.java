package edu.hw7;

import java.math.BigInteger;
import java.util.stream.IntStream;
import lombok.experimental.UtilityClass;

/* https://habr.com/ru/articles/255761/  */
@UtilityClass
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
}
