package edu.hw3.task6;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StockMarketImplTest {
    private static StockMarketImpl getBasicStockMarket() {
        StockMarketImpl basicStockMarket = new StockMarketImpl();
        basicStockMarket.add(new StockImpl(5));
        basicStockMarket.add(new StockImpl(10));
        basicStockMarket.add(new StockImpl(15));
        return basicStockMarket;
    }

    private static class GetMostValuableStockAfretStockAddingTestArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(getBasicStockMarket(), new StockImpl(1), new StockImpl(15)),
                Arguments.of(getBasicStockMarket(), new StockImpl(5), new StockImpl(15)),
                Arguments.of(getBasicStockMarket(), new StockImpl(10), new StockImpl(15)),
                Arguments.of(getBasicStockMarket(), new StockImpl(15), new StockImpl(15)),
                Arguments.of(getBasicStockMarket(), new StockImpl(20), new StockImpl(20))
            );
        }
    }

    private static class GetMostValuableStockAfretSotckRemovalTestArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(getBasicStockMarket(), new StockImpl(5), new StockImpl(15)),
                Arguments.of(getBasicStockMarket(), new StockImpl(10), new StockImpl(15)),
                Arguments.of(getBasicStockMarket(), new StockImpl(15), new StockImpl(10))
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(GetMostValuableStockAfretStockAddingTestArgumentsProvider.class)
    void getMostValuableStock_AddNewStock_ReturnExpectedAnswer(
        StockMarket stockMarket,
        Stock newStock,
        Stock expectedAnswer
    ) {
        stockMarket.add(newStock);
        Stock actualAnswer = stockMarket.getMostValuableStock();
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }

    @ParameterizedTest
    @ArgumentsSource(GetMostValuableStockAfretSotckRemovalTestArgumentsProvider.class)
    void getMostValuableStock_RemoveStock_ReturnExpectedAnswer(
        StockMarket stockMarket,
        Stock stockToRemove,
        Stock expectedAnswer
    ) {
        stockMarket.remove(stockToRemove);
        Stock actualAnswer = stockMarket.getMostValuableStock();
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }

    @Test
    void getMostValuableStock_RemoveDuplicatingStock_ReturnExpectedAnswer() {
        // given
        StockMarket stockMarket = getBasicStockMarket();
        stockMarket.add(new StockImpl(15));
        Stock expectedAnswer = new StockImpl(15);

        // when
        stockMarket.remove(new StockImpl(15));
        Stock actualAnswer = stockMarket.getMostValuableStock();

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }

    @Test
    void getMostValuableStock_StockMarketIsEmpty_ReturnNull() {
        StockMarket stockMarket = new StockMarketImpl();
        Stock actualAnswer = stockMarket.getMostValuableStock();
        assertThat(actualAnswer).isNull();
    }
}
