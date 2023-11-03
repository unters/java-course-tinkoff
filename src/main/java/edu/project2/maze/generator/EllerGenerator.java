package edu.project2.maze.generator;

import edu.project2.maze.Maze;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;
import static edu.project2.maze.Maze.Cell;

/* Singleton.  */
public class EllerGenerator implements Generator {
    private static final EllerGenerator ELLER_GENERATOR_INSTANCE = new EllerGenerator();

    private static final int MIN_HEIGHT = 5;
    private static final int MIN_WIDTH = 5;

    public static EllerGenerator getInstance() {
        return ELLER_GENERATOR_INSTANCE;
    }

    @Override
    public Maze generate(int height, int width, long seed) {
        if (height % 2 != 1 || width % 2 != 1) {
            throw new IllegalArgumentException("height and width must be odd");
        }

        if (height < MIN_HEIGHT || width < MIN_WIDTH) {
            throw new IllegalArgumentException("height and width must be greater (or equal) than 5");
        }

        Random random = new Random(seed);
        Maze maze = initializeMaze(height, width);

        int[] firstRowEquivClass = generateFirstRow(maze, random);
        int[] penultimateRowEquivClass = generateMiddleRows(maze, firstRowEquivClass, random);
        generateLastRow(maze, penultimateRowEquivClass, random);

        return maze;
    }

    private Maze initializeMaze(int height, int width) {
        Maze maze = new Maze(height, width);
        for (int y = 1; y < height - 1; ++y) {
            for (int x = 1; x < width - 1; ++x) {
                if (!(y % 2 == 0 && x % 2 == 0)) {
                    maze.setCellAt(y, x, Cell.PASSAGE);
                }
            }
        }
        return maze;
    }

    private int[] generateFirstRow(Maze maze, Random random) {
        int[] equivClass = new int[maze.width() / 2];
        Arrays.setAll(equivClass, i -> i + 1);
        for (int i = 0; i < equivClass.length - 1; ++i) {
            if (random.nextBoolean()) {
                maze.setCellAt(1, 2 * i + 2, Cell.WALL);
            } else {
                equivClass[i + 1] = equivClass[i];
            }
        }

        Deque<Integer> indices = new LinkedList<>();
        for (int i = 0; i < equivClass.length - 1; ++i) {
            if (equivClass[i] != equivClass[i + 1]) {
                indices.addLast(i);
            }
        }

        int prevIndex = -1;
        while (!indices.isEmpty()) {
            int currIndex = indices.pollFirst();
            int segmentSize = currIndex - prevIndex;
            for (int i = 0; i < segmentSize - 1; ++i) {
                int cellIndex = 2 * (random.nextInt(segmentSize) + prevIndex + 1) + 1;
                maze.setCellAt(2, cellIndex, Cell.WALL);
            }

            prevIndex = currIndex;
        }

        int currIndex = equivClass.length - 1;
        int segmentSize = currIndex - prevIndex;
        for (int i = 0; i < segmentSize - 1; ++i) {
            int cellIndex = 2 * (random.nextInt(segmentSize) + prevIndex + 1) + 1;
            maze.setCellAt(2, cellIndex, Cell.WALL);
        }

        return equivClass;
    }

    @SuppressWarnings("MagicNumber")
    private int[] generateMiddleRows(Maze maze, int[] equivClass, Random random) {
        int classCounter = equivClass.length;
        int[] prevEquivClass = equivClass;
        for (int y = 3; y < maze.height() - 2; y += 2) {
            int[] currEquivClass = Arrays.copyOf(prevEquivClass, prevEquivClass.length);
            for (int i = 0; i < currEquivClass.length; ++i) {
                if (!maze.isPassage(y - 1, 2 * i + 1)) {
                    currEquivClass[i] = ++classCounter;
                }
            }

            for (int i = 0; i < currEquivClass.length - 1; ++i) {
                if (currEquivClass[i + 1] == currEquivClass[i] || random.nextBoolean()) {
                    maze.setCellAt(y, 2 * i + 2, Cell.WALL);
                } else {
                    currEquivClass[i + 1] = currEquivClass[i];
                }
            }

            Deque<Integer> indices = new LinkedList<>();
            for (int i = 0; i < currEquivClass.length - 1; ++i) {
                if (currEquivClass[i] != currEquivClass[i + 1]) {
                    indices.addLast(i);
                }
            }

            int prevIndex = -1;
            while (!indices.isEmpty()) {
                int currIndex = indices.pollFirst();
                int segmentSize = currIndex - prevIndex;
                for (int i = 0; i < segmentSize - 1; ++i) {
                    int cellIndex = 2 * (random.nextInt(segmentSize) + prevIndex + 1) + 1;
                    maze.setCellAt(y + 1, cellIndex, Cell.WALL);
                }

                prevIndex = currIndex;
            }

            int currIndex = currEquivClass.length - 1;
            int segmentSize = currIndex - prevIndex;
            for (int i = 0; i < segmentSize - 1; ++i) {
                int cellIndex = 2 * (random.nextInt(segmentSize) + prevIndex + 1) + 1;
                maze.setCellAt(y + 1, cellIndex, Cell.WALL);
            }

            prevEquivClass = currEquivClass;
        }

        return prevEquivClass;
    }

    @SuppressWarnings("MagicNumber")
    private void generateLastRow(Maze maze, int[] prevEquivClass, Random random) {
        int maxEquivClass = -1;
        for (int value : prevEquivClass) {
            maxEquivClass = Math.max(maxEquivClass, value);
        }

        int[] currEquivClass = Arrays.copyOf(prevEquivClass, prevEquivClass.length);
        for (int i = 0; i < currEquivClass.length; ++i) {
            if (!maze.isPassage(maze.height() - 3, 2 * i + 1)) {
                currEquivClass[i] = ++maxEquivClass;
            }
        }

        for (int i = 0; i < prevEquivClass.length - 1; ++i) {
            if (currEquivClass[i + 1] == currEquivClass[i] || random.nextBoolean()) {
                maze.setCellAt(maze.height() - 2, 2 * i + 2, Cell.WALL);
            } else {
                currEquivClass[i + 1] = currEquivClass[i];
            }
        }

        for (int i = 0; i < currEquivClass.length - 1; ++i) {
            if (currEquivClass[i] != currEquivClass[i + 1]) {
                maze.setCellAt(maze.height() - 2,  2 * i + 2, Cell.PASSAGE);
            }
        }
    }

    private EllerGenerator() {
    }
}
