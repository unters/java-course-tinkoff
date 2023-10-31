package edu.project2.maze;

public final class Maze {
    private final int height;
    private final int width;
    private final Cell[][] grid;

    public Maze(int height, int width) {
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

    public enum Cell {
        WALL,
        PASSAGE
    }

    public record Coordinate(int y, int x) {
    }
}
