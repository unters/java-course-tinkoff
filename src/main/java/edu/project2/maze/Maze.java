package edu.project2.maze;

public final class Maze {
    private static final String COORDINATE_OUT_OF_GRID_MESSAGE = "given coordinate is out of grid";
    private static final String INVALID_HEIGHT_AND_WIDTH = "height and width must be greater than 2";
    private static final String NULL_CELL_MESSAGE = "cell cannot be null";

    private final int height;
    private final int width;
    private final Cell[][] grid;

    public Maze(int height, int width) {
        if (height <= 2 || width <= 2) {
            throw new IllegalArgumentException(INVALID_HEIGHT_AND_WIDTH);
        }

        grid = new Cell[height][width];
        this.height = height;
        this.width = width;
    }

    public int height() {
        return height;
    }

    public int width() {
        return width;
    }

    public Cell cellAt(int y, int x) {
        if (y < 0 || x < 0 || y >= height || x >= width) {
            throw new IllegalArgumentException(COORDINATE_OUT_OF_GRID_MESSAGE);
        }

        Cell cell = grid[y][x];
        return switch (cell) {
            case WALL -> Cell.WALL;
            case PASSAGE -> Cell.PASSAGE;
        };
    }

    public Cell cellAt(Coordinate coord) {
        return cellAt(coord.y(), coord.x());
    }

    public void setCellAt(int y, int x, Cell cell) {
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

    public record Coordinate(int y, int x) {
    }
}
