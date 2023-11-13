package edu.project2.maze;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;

public final class Maze {
    private static final String COORDINATE_OUT_OF_GRID_MESSAGE = "given coordinate is out of grid";
    private static final String INVALID_HEIGHT_AND_WIDTH = "height and width must be greater than 2";
    private static final String NULL_CELL_MESSAGE = "cell cannot be null";

    @Getter(AccessLevel.PUBLIC)
    private final int height;
    @Getter(AccessLevel.PUBLIC)
    private final int width;
    private final Cell[][] grid;

    public Maze(int height, int width) {
        if (height <= 2 || width <= 2) {
            throw new IllegalArgumentException(INVALID_HEIGHT_AND_WIDTH);
        }

        this.height = height;
        this.width = width;
        grid = new Cell[height][width];
        for (int y = 0; y < height; ++y) {
            Arrays.fill(grid[y], Cell.WALL);
        }
    }

    public Cell getCellAt(int y, int x) {
        if (y < 0 || x < 0 || y >= height || x >= width) {
            throw new IllegalArgumentException(COORDINATE_OUT_OF_GRID_MESSAGE);
        }

        return grid[y][x];
    }

    public Cell getCellAt(Coordinate coordinate) {
        return getCellAt(coordinate.y(), coordinate.x());
    }

    public void setCellAt(int y, int x, Cell cell) {
        if (y < 0 || x < 0 || y >= height || x >= width) {
            throw new IllegalArgumentException(COORDINATE_OUT_OF_GRID_MESSAGE);
        }

        if (cell == null) {
            throw new IllegalArgumentException(NULL_CELL_MESSAGE);
        }

        if (height <= 2 || width <= 2) {
            throw new IllegalArgumentException(INVALID_HEIGHT_AND_WIDTH);
        }

        grid[y][x] = cell;
    }

    public void setCellAt(Coordinate coordinate, Cell cell) {
        setCellAt(coordinate.y(), coordinate.x(), cell);
    }

    public boolean isPassage(int y, int x) {
        if (y < 0 || x < 0 || y >= height || x >= width) {
            throw new IllegalArgumentException(COORDINATE_OUT_OF_GRID_MESSAGE);
        }

        return Cell.PASSAGE.equals(grid[y][x]);
    }

    public boolean isPassage(Coordinate coordinate) {
        return isPassage(coordinate.y(), coordinate.x());
    }

    public enum Cell {
        WALL,
        PASSAGE
    }
}
