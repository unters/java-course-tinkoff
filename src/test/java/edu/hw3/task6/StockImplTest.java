package edu.hw3.task6;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StockImplTest {
    private static class StockToCompareArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(new StockImpl(10), new StockImpl(11), -1),
                Arguments.of(new StockImpl(11), new StockImpl(11), 0),
                Arguments.of(new StockImpl(12), new StockImpl(11), 1)
            );
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, -1, 0})
    void constructor_InvalidArgumentPassed_ThrowIllegalArgumentException(int cost) {
        assertThrows(IllegalArgumentException.class, () -> {
            new StockImpl(cost);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, Integer.MAX_VALUE})
    void constructor_ValidArgumentPassed_NoExceptionIsThrown(int cost) {
        assertDoesNotThrow(() -> {
            new StockImpl(cost);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(StockToCompareArgumentsProvider.class)
    void compareTo_MethodCalled_ReturnExpectedAnswer(Stock stock1, Stock stock2, int expectedAnswer) {
        int actualAnswer = stock1.compareTo(stock2);
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
