package edu.project2.maze.generator;

import edu.project2.maze.Maze;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.Deque;
import java.util.LinkedList;
import static edu.project2.maze.Maze.Cell;
import static edu.project2.maze.Maze.Coordinate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EllerGeneratorTest {
    private static final EllerGenerator ellerGenerator = EllerGenerator.getInstance();

    @ParameterizedTest
    @CsvSource({"-100, 5", "15, -2", "0, 0", "3, 3", "4, 7", "19, 20", "100, 100"})
    void generate_InvalidArgumentsGiven_ThrowIllegalArgumentsException(int height, int width) {
        assertThrows(IllegalArgumentException.class, () -> {
            ellerGenerator.generate(height, width);
        });
    }

    @ParameterizedTest
    @CsvSource({"5, 5", "17, 15", "51, 55", "729, 151", "117, 28643"})
    void generate_ValidArgumentsGiven_NoExceptionIsThrown(int height, int width) {
        assertDoesNotThrow(() -> {
            ellerGenerator.generate(height, width);
        });
    }

    @ParameterizedTest
    @CsvSource({"5, 5", "15, 5", "5, 21", "51, 47", "189, 211"})
    void generate_ValidArgumentsGiven_AllCellsAreReachableFromAnyCell(int height, int width) {
        Maze maze = ellerGenerator.generate(height, width);
        assertThat(EllerGeneratorMazeChecker.check(maze)).isTrue();
    }

    private final static class EllerGeneratorMazeChecker {
        private static final int[][] steps = new int[][] {
            {-1, 0}, {0, 1}, {1, 0}, {0, -1}
        };

        static boolean check(Maze maze) {
            int height = maze.height();
            int width = maze.width();

            Deque<Coordinate> queue = new LinkedList<>();
            boolean[][] visited = new boolean[height][width];

            queue.addLast(new Coordinate(1, 1));
            while (!queue.isEmpty()) {
                Coordinate coordinate = queue.pollFirst();
                int y = coordinate.y();
                int x = coordinate.x();
                visited[y][x] = true;

                for (int[] step : steps) {
                    int yAdj = y + step[0];
                    int xAdj = x + step[1];
                    if (yAdj >= 0 && yAdj < height && xAdj >= 0 && xAdj < width &&
                        maze.getCellAt(yAdj, xAdj).equals(Cell.PASSAGE) && !visited[yAdj][xAdj]) {
                        queue.addLast(new Coordinate(yAdj, xAdj));
                    }
                }
            }

            for (int y = 1; y < height; y += 2) {
                for (int x = 1; x < width; x += 2) {
                    if (!visited[y][x]) {
                        return false;
                    }
                }
            }

            return true;
        }

        private EllerGeneratorMazeChecker() {
        }
    }
}
