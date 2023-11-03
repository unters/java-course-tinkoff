package edu.project2.maze.generator;

import edu.project2.maze.Maze;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import static edu.project2.maze.Maze.Cell;
import static edu.project2.maze.Maze.Coordinate;

public class PrimGenerator implements Generator {
    private static final PrimGenerator PRIM_GENERATOR_INSTANCE = new PrimGenerator();

    private static final int[][] STEPS = new int[][] {
        {-2, 0}, {0, 2}, {2, 0}, {0, -2}
    };

    public static PrimGenerator getInstance() {
        return PRIM_GENERATOR_INSTANCE;
    }

    @Override
    public Maze generate(int height, int width, long seed) {
        if (height % 2 != 1 || width % 2 != 1) {
            throw new IllegalArgumentException("height and width must be odd");
        }

        Random random = new Random(seed);
        Maze maze = new Maze(height, width);
        LinkedList<Coordinate> coordinatesPool = new LinkedList<>();

        int yStart = 1 + 2 * random.nextInt((height - 1) / 2);
        int xStart = 1 + 2 * random.nextInt((width - 1) / 2);
        maze.setCellAt(yStart, xStart, Cell.PASSAGE);
        for (int[] step : STEPS) {
            int yAdj = yStart + step[0];
            int xAdj = xStart + step[1];
            if (yAdj >= 0 && yAdj < height && xAdj >= 0 && xAdj < width) {
                coordinatesPool.add(new Coordinate(yAdj, xAdj));
            }
        }

        while (!coordinatesPool.isEmpty()) {
            Coordinate coordinate = coordinatesPool.remove(random.nextInt(coordinatesPool.size()));
            if (maze.isPassage(coordinate)) {
                continue;
            }

            int y = coordinate.y();
            int x = coordinate.x();
            maze.setCellAt(y, x, Cell.PASSAGE);
            List<Coordinate> adjacentPassages = new ArrayList<>();
            for (int[] step : STEPS) {
                int yAdj = y + step[0];
                int xAdj = x + step[1];
                if (yAdj >= 0 && yAdj < height && xAdj >= 0 && xAdj < width) {
                    if (maze.isPassage(yAdj, xAdj)) {
                        adjacentPassages.add(new Coordinate(y + (step[0] / 2), x + (step[1] / 2)));
                    } else {
                        coordinatesPool.add(new Coordinate(yAdj, xAdj));
                    }
                }
            }

            maze.setCellAt(adjacentPassages.get(random.nextInt(adjacentPassages.size())), Cell.PASSAGE);
        }

        return maze;
    }

    private PrimGenerator() {
    }
}
