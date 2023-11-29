package edu.hw7;

import java.math.BigInteger;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import static edu.hw7.Task2.calculateFactorialInParallel;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task2Test {
    private static class ValidArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                Arguments.of(0, BigInteger.valueOf(1)),
                Arguments.of(1, BigInteger.valueOf(1)),
                Arguments.of(2, BigInteger.valueOf(2)),
                Arguments.of(3, BigInteger.valueOf(6)),
                Arguments.of(4, BigInteger.valueOf(24)),
                Arguments.of(5, BigInteger.valueOf(120)),
                Arguments.of(10, BigInteger.valueOf(3_628_800)),
                Arguments.of(15, BigInteger.valueOf(1_307_674_368_000L)),
                Arguments.of(20, BigInteger.valueOf(2_432_902_008_176_640_000L))
            );
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -42, -103_212})
    void calculateFactorialInParallel_InvalidArgumentsGiven_IllegalArgumentsExceptionIsThrown(int n) {
        assertThrows(IllegalArgumentException.class, () -> {
            calculateFactorialInParallel(n);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(ValidArgumentsProvider.class)
    void calculateFactorialInParallel_ValidArgumentsGiven_ReturnExpectedAnswer(int n, BigInteger expectedAnswer) {
        // when
        BigInteger actualAnswer = calculateFactorialInParallel(n);

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
