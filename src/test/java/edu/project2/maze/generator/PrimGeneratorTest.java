package edu.project2.maze.generator;

import edu.project2.maze.Maze;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PrimGeneratorTest {
    private static final PrimGenerator primGenerator = PrimGenerator.getInstance();

    @ParameterizedTest
    @CsvSource({"-100, 5", "15, -2", "0, 0", "1, 1", "4, 7", "19, 20", "100, 100"})
    void generate_InvalidArgumentsGiven_ThrowIllegalArgumentsException(int height, int width) {
        assertThrows(IllegalArgumentException.class, () -> {
            primGenerator.generate(height, width);
        });
    }

    @ParameterizedTest
    @CsvSource({"3, 3", "5, 5", "17, 15", "51, 55", "729, 151", "117, 28643"})
    void generate_ValidArgumentsGiven_NoExceptionIsThrown(int height, int width) {
        assertDoesNotThrow(() -> {
            primGenerator.generate(height, width);
        });
    }

    @ParameterizedTest
    @CsvSource({"5, 5", "15, 5", "5, 21", "51, 47", "189, 211"})
    void generate_ValidArgumentsGiven_AllCellsAreReachableFromAnyCell(int height, int width) {
        Maze maze = primGenerator.generate(height, width);
        assertThat(MazeChecker.check(maze)).isTrue();
    }
}
