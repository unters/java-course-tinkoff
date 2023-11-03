package edu.project2.maze.generator;

import edu.project2.maze.Maze;
import java.util.Deque;
import java.util.LinkedList;
import static edu.project2.maze.Maze.Coordinate;

public class MazeChecker {
    private static final int[][] steps = new int[][] {
        {-1, 0}, {0, 1}, {1, 0}, {0, -1}
    };

    /* Check if any cell with both coordinates odd is accessible from any other cell with both coordinates odd.  */
    public static boolean check(Maze maze) {
        int height = maze.height();
        int width = maze.width();

        Deque<Coordinate> queue = new LinkedList<>();
        boolean[][] visited = new boolean[height][width];

        /* All implemented maze generators generate mazes that are guaranteed to have passages at both coordinates
         * odd.  */
        queue.addLast(new Maze.Coordinate(1, 1));
        while (!queue.isEmpty()) {
            Maze.Coordinate coordinate = queue.pollFirst();
            int y = coordinate.y();
            int x = coordinate.x();
            visited[y][x] = true;

            for (int[] step : steps) {
                int yAdj = y + step[0];
                int xAdj = x + step[1];
                if (yAdj >= 0 && yAdj < height && xAdj >= 0 && xAdj < width &&
                    maze.isPassage(yAdj, xAdj) && !visited[yAdj][xAdj]) {
                    queue.addLast(new Coordinate(yAdj, xAdj));
                }
            }
        }

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                if (maze.isPassage(y, x) && !visited[y][x]) {
                    return false;
                }
            }
        }

        return true;
    }

    private MazeChecker() {
    }
}
