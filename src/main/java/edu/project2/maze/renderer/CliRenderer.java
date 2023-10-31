package edu.project2.maze.renderer;

import edu.project2.maze.Maze;
import java.util.List;

public class CliRenderer implements Renderer {
    @Override
    public void render(Maze maze) {
        int height = maze.height();
        int width = maze.width();
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                System.out.print(maze.isPassage(y, x) ? "   " : "XXX");
            }
            System.out.println();
        }
    }

    @Override
    public void render(Maze maze, List<Maze.Coordinate> path) {

    }
}
