package edu.hw2.Task2;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task2Test {
    private static final class ValidRectanglesArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(new Rectangle((short) 15, (short) 12), (short) 180),
                Arguments.of(new Square((short) 10, (short) 10), (short) 100)
            );
        }
    }

    @ParameterizedTest
    @CsvSource({"-15, 1", "12, -10", "0, 2", "-10, -10"})
    void rectangleConstructor_NonPositiveArgumentsPassed_ThrowIllegalArgumentsException(short width, short height) {
        assertThrows(IllegalArgumentException.class, () -> {
            Rectangle rectangle = new Rectangle(width, height);
        });
    }

    @ParameterizedTest
    @CsvSource({"-15, 1", "12, -10", "0, 2", "-10, -10"})
    void squareConstructor_NonPositiveArgumentsPassed_ThrowIllegalArgumentsException(short width, short height) {
        assertThrows(IllegalArgumentException.class, () -> {
            Rectangle rectangle = new Square(width, height);
        });
    }

    @ParameterizedTest
    @CsvSource({"15, 1", "12, 10"})
    void squareConstructor_PositiveNotEqualArgumentsPassed_ThrowIllegalArgumentsException(short width, short height) {
        assertThrows(IllegalArgumentException.class, () -> {
            Rectangle rectangle = new Square(width, height);
        });
    }

    @ParameterizedTest
    @CsvSource({"15, 1", "12, 10"})
    void rectangleConstructor_PositiveNotEqualArgumentsPassed_NoExceptionIsThrown(short width, short height) {
        assertDoesNotThrow(() -> {
            Rectangle rectangle = new Rectangle(width, height);
        });
    }

    @ParameterizedTest
    @CsvSource({"1, 1", "10, 10"})
    void squareConstructor_PositiveEqualArgumentsPassed_NoExceptionIsThrown(short width, short height) {
        assertDoesNotThrow(() -> {
            Rectangle rectangle = new Square(width, height);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(ValidRectanglesArgumentsProvider.class)
    void getArea_RectangleIsValid_ReturnExpectedAnswer(Rectangle rectangle, int expectedAnswer) {
        int actualAnswer = rectangle.getArea();
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
