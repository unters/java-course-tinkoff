package edu.project2.maze;

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
import static edu.project2.maze.Maze.Cell;

public class MazeTest {
    private static final class GetCellAtInvalidArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(new Maze(10, 10), new Coordinate(10, 10)),
                Arguments.of(new Maze(15, 22), new Coordinate(14, 22)),
                Arguments.of(new Maze(23, 11), new Coordinate(30, 1)),
                Arguments.of(new Maze(10, 70), new Coordinate(-15, 5)),
                Arguments.of(new Maze(23, 11), new Coordinate(-1, -1)),
                Arguments.of(new Maze(99, 99), new Coordinate(2, -98))
            );
        }
    }

    private static final class GetCellAtValidArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(new Maze(10, 10), new Coordinate(9, 9)),
                Arguments.of(new Maze(10, 10), new Coordinate(0, 0)),
                Arguments.of(new Maze(10, 10), new Coordinate(2, 5)),
                Arguments.of(new Maze(15, 22), new Coordinate(2, 10)),
                Arguments.of(new Maze(23, 11), new Coordinate(22, 0))
            );
        }
    }

    private static final class SetCellAtInvalidArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(new Maze(10, 10), new Coordinate(10, 10), Cell.WALL),
                Arguments.of(new Maze(15, 22), new Coordinate(14, 22), Cell.PASSAGE),
                Arguments.of(new Maze(23, 11), new Coordinate(30, 1), Cell.PASSAGE),
                Arguments.of(new Maze(10, 70), new Coordinate(-15, 5), Cell.WALL),
                Arguments.of(new Maze(23, 11), new Coordinate(-1, -1), Cell.PASSAGE),
                Arguments.of(new Maze(99, 99), new Coordinate(2, -98), Cell.WALL)
            );
        }
    }

    private static final class IsPassageInvalidArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(new Maze(10, 10), new Coordinate(10, 10)),
                Arguments.of(new Maze(15, 22), new Coordinate(14, 22)),
                Arguments.of(new Maze(23, 11), new Coordinate(30, 1)),
                Arguments.of(new Maze(10, 70), new Coordinate(-15, 5)),
                Arguments.of(new Maze(23, 11), new Coordinate(-1, -1)),
                Arguments.of(new Maze(99, 99), new Coordinate(2, -98))
            );
        }
    }

    private static final class SetCellAtToPassageValidArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(new Maze(10, 10), new Coordinate(9, 9)),
                Arguments.of(new Maze(10, 10), new Coordinate(0, 0)),
                Arguments.of(new Maze(10, 10), new Coordinate(2, 5)),
                Arguments.of(new Maze(15, 22), new Coordinate(2, 10)),
                Arguments.of(new Maze(23, 11), new Coordinate(22, 0))
            );
        }
    }

    @ParameterizedTest
    @CsvSource({"-100, -100", "27, -32", "-13, 517", "0, 0", "2, 3", "5, 2", "2, 2"})
    void new_InvalidArgumentsGiven_ThrowIllegalArgumentException(int height, int width) {
        assertThrows(IllegalArgumentException.class, () -> {
            new Maze(height, width);
        });
    }

    @ParameterizedTest
    @CsvSource({"3, 3", "3, 100", "12, 3", "14, 75", "512, 729"})
    void new_ValidArgumentsGiven_NoExceptionIsThrown(int height, int width) {
        assertDoesNotThrow(() -> {
            new Maze(height, width);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(GetCellAtInvalidArgumentsProvider.class)
    void getCellAt_InvalidCoordinatesGiven_ThrowIllegalArgumentException(Maze maze, Coordinate coordinate) {
        assertThrows(IllegalArgumentException.class, () -> {
            maze.getCellAt(coordinate);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(GetCellAtValidArgumentsProvider.class)
    void getCellAt_ValidCoordinatesGiven_NoExceptionIsThrown(Maze maze, Coordinate coordinate) {
        assertDoesNotThrow(() -> {
            maze.getCellAt(coordinate);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(SetCellAtInvalidArgumentsProvider.class)
    void setCellAt_InvalidCoordinateGiven_ThrowIllegalArgumentException(Maze maze, Coordinate coordinate, Cell cell) {
        assertThrows(IllegalArgumentException.class, () -> {
            maze.setCellAt(coordinate, cell);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(IsPassageInvalidArgumentsProvider.class)
    void isPassage_InvalidCoordinateGiven_ThrowIllegalArgumentException(Maze maze, Coordinate coordinate) {
        assertThrows(IllegalArgumentException.class, () -> {
            maze.isPassage(coordinate);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(SetCellAtToPassageValidArgumentsProvider.class)
    void setCellAt_SetCellAtGivenCoordinateToPassage_isPassageReturnsTrue(Maze maze, Coordinate coordinate) {
        maze.setCellAt(coordinate, Cell.PASSAGE);
        boolean actualAnswer = maze.isPassage(coordinate);
        assertThat(actualAnswer).isTrue();
    }
}
