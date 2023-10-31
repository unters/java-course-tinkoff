package edu.project2.maze.renderer;

import edu.project2.maze.Maze;
import java.util.List;
import static edu.project2.maze.Maze.Coordinate;


public interface Renderer {
    void render(Maze maze);

    void render(Maze maze, List<Coordinate> path);
}
