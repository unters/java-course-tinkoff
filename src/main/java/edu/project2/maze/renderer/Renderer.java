package edu.project2.maze.renderer;

import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import java.util.List;

public interface Renderer {
    void render(Maze maze);

    void render(Maze maze, List<Coordinate> path);
}
