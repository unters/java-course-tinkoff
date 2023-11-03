package edu.project2.maze.solver;

import edu.project2.maze.Maze;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import static edu.project2.maze.Maze.Cell;
import static edu.project2.maze.Maze.Coordinate;

public class BfsSolver implements Solver {
    private static final BfsSolver BFS_SOLVER_INSTANCE = new BfsSolver();

    private static final int[][] STEPS = new int[][] {
        {-1, 0}, {0, 1}, {1, 0}, {0, -1}
    };

    public static BfsSolver getInstance() {
        return BFS_SOLVER_INSTANCE;
    }

    @Override
    public Optional<List<Coordinate>> solve(Maze maze, Coordinate start, Coordinate end) {
        if (!maze.isPassage(start) || !maze.isPassage(end)) {
            throw new IllegalArgumentException("start and finish must be located at passages");
        }

        if (start.equals(end)) {
            return Optional.of(List.of(start));
        }

        int height = maze.height();
        int width = maze.width();
        long[][] distance = new long[height][width];
        Coordinate[][] previous = new Coordinate[height][width];
        for (int i = 0; i < height; ++i) {
            Arrays.fill(distance[i], Long.MAX_VALUE);
        }

        Deque<Coordinate> queue = new LinkedList<>();
        queue.addLast(start);
        distance[start.y()][start.x()] = 0;
        while (!queue.isEmpty()) {
            Coordinate coordinate = queue.pollFirst();
            int y = coordinate.y();
            int x = coordinate.x();

            for (int[] step : STEPS) {
                int yAdj = y + step[0];
                int xAdj = x + step[1];
                if (yAdj >= 0 && yAdj < height && xAdj >= 0 && xAdj < width
                    && maze.getCellAt(yAdj, xAdj).equals(Cell.PASSAGE) && distance[yAdj][xAdj] > distance[y][x] + 1) {
                    distance[yAdj][xAdj] = distance[y][x] + 1;
                    previous[yAdj][xAdj] = coordinate;
                    queue.addLast(new Coordinate(yAdj, xAdj));
                }
            }
        }

        if (previous[end.y()][end.x()] == null) {
            return Optional.empty();
        }

        List<Coordinate> answer = new ArrayList<>();
        Coordinate coordinate = end;
        while (coordinate != null) {
            answer.add(coordinate);
            coordinate = previous[coordinate.y()][coordinate.x()];
        }

        return Optional.of(answer);
    }

    private BfsSolver() {
    }
}
