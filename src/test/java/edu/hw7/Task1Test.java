package edu.hw7;

import edu.hw7.task1.Counter;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import static edu.hw7.Task1.executeProcedure;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task1Test {
    private static class InvalidArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                Arguments.of(new Counter(0), 0),
                Arguments.of(new Counter(0), -15),
                Arguments.of(null, 20),
                Arguments.of(null, -1)
            );
        }
    }

    private static class ValidArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                Arguments.of(new Counter(0), 100_000, 0),
                Arguments.of(new Counter(-13), 12385, -13),
                Arguments.of(new Counter(42), 927_193, 42)
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(InvalidArgumentsProvider.class)
    void executeProcedure_InvalidArgumentGiven_IllegalArgumentExceptionIsThrown(Counter counter, int t) {
        assertThrows(IllegalArgumentException.class, () -> {
            executeProcedure(counter, t);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(ValidArgumentsProvider.class)
    void executeProcedure_ValidParametersGiven_CounterRemainsTheSame(Counter counter, int t, int expectedValue) {
        // when
        executeProcedure(counter, t);

        // then
        int actualValue = counter.getValue();
        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
