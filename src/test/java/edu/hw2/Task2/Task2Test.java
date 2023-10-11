package edu.hw2.Task2;

import static edu.hw2.Task2.Rectangle.RectangleProperties;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {
    static Arguments[] rectangles() {
        return new Arguments[]{
            Arguments.of(new Rectangle()),
            Arguments.of(new Square())
        };
    }

    @ParameterizedTest
    @MethodSource("rectangles")
    void rectangleArea(Rectangle rectangle) {
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
}
