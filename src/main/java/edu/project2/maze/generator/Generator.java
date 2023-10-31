package edu.project2.maze.generator;

import edu.project2.maze.Maze;

public interface Generator {
    Maze generate(int height, int width);

    Maze generate(int height, int width, long seed);
}
