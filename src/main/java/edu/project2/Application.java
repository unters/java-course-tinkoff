package edu.project2;

import edu.project2.maze.Maze;
import edu.project2.maze.generator.DfsGenerator;
import edu.project2.maze.generator.Generator;
import edu.project2.maze.renderer.CliRenderer;
import edu.project2.maze.renderer.Renderer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

final class Application {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final int DEFAULT_HEIGHT = 32;
    private static final int DEFAULT_WIDTH = 32;

    /* Parameters.  */
    private static int height = DEFAULT_HEIGHT;
    private static int width = DEFAULT_WIDTH;

    static void run() {
        Generator generator = DfsGenerator.getInstance();
        Maze maze = generator.generate(11, 31, 42);
        Renderer renderer = new CliRenderer();
        renderer.render(maze);
    }

    private Application() {
    }
}
