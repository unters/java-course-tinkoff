package edu.hw2.Task2;

import static edu.hw2.Task2.Rectangle.RectangleProperties;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task2Test {
    private static final class RectanglesWithNegativeSidesArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(new Rectangle(), (short) -12),
                Arguments.of(new Square(), (short) -15)
            );
        }
    }

    static Arguments[] rectangles() {
        return new Arguments[] {
            Arguments.of(new Rectangle()),
            Arguments.of(new Square())
        };
    }

    @ParameterizedTest
    @MethodSource("rectangles")
    void getArea_givenRectangleObject_ReturnAnswerThatCorrespondsRectangleProperties(Rectangle rectangle) {
        // given
        RectangleProperties properties;
        rectangle.setWidth((short) 20);
        properties = rectangle.setHeight((short) 10);

        // when
        int actualAnswer = rectangle.getArea();
        int expectedAnswer = properties.width() * properties.height();

        // then
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }

    @ParameterizedTest
    @ArgumentsSource(RectanglesWithNegativeSidesArgumentsProvider.class)
    void setWidth_negativeWidthGiven_ThrowIllegalArgumentException(Rectangle rectangle, short width) {
        assertThrows(IllegalArgumentException.class, () -> {
            rectangle.setWidth(width);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(RectanglesWithNegativeSidesArgumentsProvider.class)
    void setHeight_negativeWidthGiven_ThrowIllegalArgumentException(Rectangle rectangle, short height) {
        assertThrows(IllegalArgumentException.class, () -> {
            rectangle.setHeight(height);
        });
    }
}
