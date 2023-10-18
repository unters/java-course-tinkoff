package edu.hw2.task2;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task2Test {
    private static final class ValidRectanglesArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(new Rectangle(15, 12), 180),
                Arguments.of(new Square(10, 10), 100)
            );
        }
    }

    private static final class RectangleSetWidthOrHeightInvalidArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(new Rectangle(15, 12), -10),
                Arguments.of(new Square(10, 10), -13),
                Arguments.of(new Rectangle(15, 12), 0),
                Arguments.of(new Square(10, 10), 0)
            );
        }
    }

    private static final class GetAreaAfterChangingHeightTestArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(new Rectangle(15, 12), 10, 150),
                Arguments.of(new Square(10, 10), 13, 130)
            );
        }
    }

    private static final class GetAreaAfterChangingWidthTestArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(new Rectangle(15, 12), 10, 120),
                Arguments.of(new Square(10, 10), 15, 150)
            );
        }
    }

    private static final class SquareSetSideInvalidArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(new Square(15), -52),
                Arguments.of(new Square(10), 0)
            );
        }
    }

    private static final class GetAreaAfterChangingSquareSideTestArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(new Square(15), 10, 100),
                Arguments.of(new Square(10, 10), 15, 225)
            );
        }
    }

    @ParameterizedTest
    @CsvSource({"-15, 1", "12, -10", "0, 2", "-10, -10"})
    void rectangleConstructor_NonPositiveArgumentsPassed_ThrowIllegalArgumentsException(int width, int height) {
        assertThrows(IllegalArgumentException.class, () -> {
            Rectangle rectangle = new Rectangle(width, height);
        });
    }

    @ParameterizedTest
    @CsvSource({"-15, 1", "12, -10", "0, 2", "-10, -10"})
    void squareWidthHeightConstructor_NonPositiveArgumentsPassed_ThrowIllegalArgumentsException(int width, int height) {
        assertThrows(IllegalArgumentException.class, () -> {
            Rectangle rectangle = new Square(width, height);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {-12, 0})
    void squareSideConstructor_NonPositiveArgumentsPassed_ThrowIllegalArgumentsException(int side) {
        assertThrows(IllegalArgumentException.class, () -> {
            Rectangle rectangle = new Square(side);
        });
    }

    @ParameterizedTest
    @CsvSource({"15, 1", "12, 10"})
    void squareWidthHeightConstructor_PositiveNotEqualArgumentsPassed_ThrowIllegalArgumentsException(
        int width,
        int height
    ) {
        assertThrows(IllegalArgumentException.class, () -> {
            Rectangle rectangle = new Square(width, height);
        });
    }

    @ParameterizedTest
    @CsvSource({"15, 1", "12, 10"})
    void rectangleConstructor_PositiveNotEqualArgumentsPassed_NoExceptionIsThrown(int width, int height) {
        assertDoesNotThrow(() -> {
            Rectangle rectangle = new Rectangle(width, height);
        });
    }

    @ParameterizedTest
    @CsvSource({"1, 1", "10, 10"})
    void squareWidthHeightConstructor_PositiveEqualArgumentsPassed_NoExceptionIsThrown(int width, int height) {
        assertDoesNotThrow(() -> {
            Rectangle rectangle = new Square(width, height);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 42, 48921})
    void squareSideConstructor_PositiveArgumentPassed_NoExceptionIsThrown(int side) {
        assertDoesNotThrow(() -> {
            Rectangle rectangle = new Square(side);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(ValidRectanglesArgumentsProvider.class)
    void getArea_RectangleIsValid_ReturnExpectedAnswer(Rectangle rectangle, long expectedAnswer) {
        long actualAnswer = rectangle.getArea();
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }

    @ParameterizedTest
    @ArgumentsSource(RectangleSetWidthOrHeightInvalidArgumentsProvider.class)
    void rectangleSetWidth_InvalidArgumentPassed_ThrowIllegalArgumentException(Rectangle rectangle, int width) {
        assertThrows(IllegalArgumentException.class, () -> {
            rectangle.setWidth(width);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(RectangleSetWidthOrHeightInvalidArgumentsProvider.class)
    void rectangleSetHeight_InvalidArgumentPassed_ThrowIllegalArgumentException(Rectangle rectangle, int height) {
        assertThrows(IllegalArgumentException.class, () -> {
            rectangle.setHeight(height);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(SquareSetSideInvalidArgumentsProvider.class)
    void squareSetSide_InvalidArgumentPassed_ThrowIllegalArgumentException(Square square, int side) {
        assertThrows(IllegalArgumentException.class, () -> {
            square.setHeight(side);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(GetAreaAfterChangingHeightTestArgumentsProvider.class)
    void getArea_ChangeHeightOfTheRectangle_ReturnExpectedAnswer(
        Rectangle rectangle,
        int newHeight,
        long expectedAnswer
    ) {
        Rectangle rectangleAfterModification = rectangle.setHeight(newHeight);
        long actualAnswer = rectangleAfterModification.getArea();
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }

    @ParameterizedTest
    @ArgumentsSource(GetAreaAfterChangingWidthTestArgumentsProvider.class)
    void getArea_ChangeWidthOfTheRectangle_ReturnExpectedAnswer(
        Rectangle rectangle,
        int newWidth,
        long expectedAnswer
    ) {
        Rectangle rectangleAfterModification = rectangle.setWidth(newWidth);
        long actualAnswer = rectangleAfterModification.getArea();
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }

    @ParameterizedTest
    @ArgumentsSource(GetAreaAfterChangingSquareSideTestArgumentsProvider.class)
    void getArea_ChangeSideOfTheSquare_ReturnExpectedAnswer(Square square, int newSide, long expectedAnswer) {
        Square rectangleAfterModification = square.setSide(newSide);
        long actualAnswer = rectangleAfterModification.getArea();
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
