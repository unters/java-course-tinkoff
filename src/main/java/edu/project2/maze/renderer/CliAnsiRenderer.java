package edu.project2.maze.renderer;

import edu.project2.maze.Maze;
import java.util.List;
import static edu.project2.maze.Maze.Coordinate;

/* Singleton.  */
public class CliAnsiRenderer implements Renderer {
    private static final CliAnsiRenderer CLI_ANSI_RENDERER = new CliAnsiRenderer();

    private static final String PASSAGE_CELL = "  ";
    private static final String WALL_CELL = "\033[40m  \033[0m";
    private static final String PATH_CELL = "\033[41m  \033[0m";

    public static CliAnsiRenderer getInstance() {
        return CLI_ANSI_RENDERER;
    }

    @Override
    public void render(Maze maze) {
        int height = maze.height();
        int width = maze.width();
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                System.out.print(maze.isPassage(y, x) ? PASSAGE_CELL : WALL_CELL);
            }
            System.out.println();
        }
    }

    @Override
    public void render(Maze maze, List<Coordinate> path) {
        int height = maze.height();
        int width = maze.width();
        String[][] grid = new String[height][width];

        /* Render maze.  */
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                grid[y][x] = maze.isPassage(y, x) ? PASSAGE_CELL : WALL_CELL;
            }
        }

        /* Render path.  */
        for (Coordinate coordinate : path) {
            grid[coordinate.y()][coordinate.x()] = PATH_CELL;
        }

        /* Print grid.  */
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                System.out.print(grid[y][x]);
            }
            System.out.println();
        }
    }

    private CliAnsiRenderer() {
    }
}
