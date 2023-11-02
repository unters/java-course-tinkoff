package edu.project2.maze.solver;

import edu.project2.maze.Maze;
import edu.project2.maze.generator.DfsGenerator;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import static edu.project2.maze.Maze.Cell;
import static edu.project2.maze.Maze.Coordinate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BfsSolverTest {
    private static final BfsSolver bfsSolver = BfsSolver.getInstance();

    private static final class InvalidArgumentsProvider implements ArgumentsProvider {
        private static final DfsGenerator dfsGenerator = DfsGenerator.getInstance();

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(dfsGenerator.generate(13, 15), new Coordinate(0, 5), new Coordinate(11, 13)),
                Arguments.of(dfsGenerator.generate(91, 3), new Coordinate(1, 1), new Coordinate(90, 1))
            );
        }
    }

    private static final class UnsolvableMazeArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(maze1(), new Coordinate(1, 1), new Coordinate(1, 3)),
                Arguments.of(maze2(), new Coordinate(1, 1), new Coordinate(3, 3))
            );
        }

        /* W W W W W
         * W   W   W
         * W W W W W  */
        private Maze maze1() {
            Maze maze = new Maze(3, 5);
            maze.setCellAt(1, 1, Cell.PASSAGE);
            maze.setCellAt(1, 3, Cell.PASSAGE);
            return maze;
        }

        /* W W W W W W W
         * W           W
         * W   W W W   W
         * W   W   W   W
         * W   W W W   W
         * W           W
         * W W W W W W W  */
        private Maze maze2() {
            Maze maze = new Maze(7, 7);
            maze.setCellAt(3, 3, Cell.PASSAGE);
            for (int i = 1; i < 6; ++i) {
                maze.setCellAt(1, i, Cell.PASSAGE);
                maze.setCellAt(5, i, Cell.PASSAGE);
                maze.setCellAt(i, 1, Cell.PASSAGE);
                maze.setCellAt(i, 5, Cell.PASSAGE);
            }

            return maze;
        }
    }

    private static final class SolvableMazeArgumentsProvider implements ArgumentsProvider {
        private static final DfsGenerator dfsGenerator = DfsGenerator.getInstance();

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(maze1(), new Coordinate(1, 1), new Coordinate(1, 1)),
                Arguments.of(maze2(), new Coordinate(1, 1), new Coordinate(1, 3)),
                Arguments.of(dfsGenerator.generate(101, 101), new Coordinate(1, 1), new Coordinate(99, 99)),
                Arguments.of(dfsGenerator.generate(3, 19001), new Coordinate(1, 1), new Coordinate(1, 18999)),
                Arguments.of(dfsGenerator.generate(553, 137), new Coordinate(1, 1), new Coordinate(313, 75))
            );
        }

        /* W W W
         * W   W
         * W W W  */
        private Maze maze1() {
            Maze maze = new Maze(3, 3);
            maze.setCellAt(1, 1, Cell.PASSAGE);
            return maze;
        }

        /* W W W W W
         * W       W
         * W W W W W  */
        private Maze maze2() {
            Maze maze = new Maze(3, 5);
            for (int x = 1; x < 5; ++x) {
                maze.setCellAt(1, x, Cell.PASSAGE);
            }

            return maze;
        }
    }

    @ParameterizedTest
    @ArgumentsSource(UnsolvableMazeArgumentsProvider.class)
    void solve_UnsolvableMazeGiven_ReturnEmptyOptional(Maze maze, Coordinate start, Coordinate end) {
        Optional<List<Coordinate>> actualAnswer = bfsSolver.solve(maze, start, end);
        assertThat(actualAnswer.isEmpty()).isTrue();
    }

    @ParameterizedTest
    @ArgumentsSource(SolvableMazeArgumentsProvider.class)
    void solve_SolvableMazeGiven_ReturnNonEmptyOptional(Maze maze, Coordinate start, Coordinate end) {
        Optional<List<Coordinate>> actualAnswer = bfsSolver.solve(maze, start, end);
        assertThat(actualAnswer.isEmpty()).isFalse();
    }

    @ParameterizedTest
    @ArgumentsSource(InvalidArgumentsProvider.class)
    void solve_InvalidCoordinatesGiven_ThrowIllegalArgumentsException(Maze maze, Coordinate start, Coordinate end) {
        assertThrows(IllegalArgumentException.class, () -> {
            bfsSolver.solve(maze, start, end);
        });
    }
}
