package edu.project2.maze.generator;

import edu.project2.maze.Maze;
import java.util.Random;

public interface Generator {
    default Maze generate(int height, int width) {
        Random random = new Random();
        long seed = random.nextLong();
        return generate(height, width, seed);
    }

    Maze generate(int height, int width, long seed);
}
