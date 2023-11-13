package edu.project2.maze.generator;

import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import static edu.project2.maze.Maze.Cell;

/* Singleton.  */
public class DfsGenerator implements Generator {
    private static final DfsGenerator DFS_GENERATOR_INSTANCE = new DfsGenerator();

    private DfsGenerator() {
    }

    private static final int[][] STEPS = new int[][] {
        {-2, 0}, {0, 2}, {2, 0}, {0, -2}
    };


    public static DfsGenerator getInstance() {
        return DFS_GENERATOR_INSTANCE;
    }

    @Override
    public Maze generate(int height, int width, long seed) {
        if (height % 2 != 1 || width % 2 != 1) {
            throw new IllegalArgumentException("DfsGenerator: height and width must be odd");
        }

        Maze maze = initializeMaze(height, width);
        /* Passages generation.  */
        Random random = new Random(seed);
        Deque<Coordinate> dq = new LinkedList<>();
        boolean[][] visited = new boolean[height][width];

        dq.add(new Coordinate(1, 1));
        while (!dq.isEmpty()) {
            Coordinate coordinate = dq.getLast();
            int y = coordinate.y();
            int x = coordinate.x();
            visited[y][x] = true;

            /* Find unvisited "adjacent" passages.  */
            List<int[]> nextSteps = new ArrayList<>();
            for (int[] step : STEPS) {
                int yNew = y + step[0];
                int xNew = x + step[1];
                if (yNew >= 0 && yNew < height && xNew >= 0 && xNew < width && !visited[yNew][xNew]) {
                    nextSteps.add(step);
                }
            }

            /* If there are unvisited "adjacent" passages choose one of them randomly.  */
            if (nextSteps.isEmpty()) {
                dq.removeLast();
            } else {
                int[] step = nextSteps.get(random.nextInt(nextSteps.size()));
                int yWall = y + step[0] / 2;
                int xWall = x + step[1] / 2;
                /* Create passage (remove wall) between current and next cells.  */
                maze.setCellAt(yWall, xWall, Cell.PASSAGE);
                int yNew = y + step[0];
                int xNew = x + step[1];
                dq.addLast(new Coordinate(yNew, xNew));
            }
        }

        return maze;
    }

    private Maze initializeMaze(int height, int width) {
        Maze maze = new Maze(height, width);
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                maze.setCellAt(y, x, Cell.WALL);
            }
        }

        for (int y = 1; y < height; y += 2) {
            for (int x = 1; x < width; x += 2) {
                maze.setCellAt(y, x, Cell.PASSAGE);
            }
        }

        return maze;
    }
}
