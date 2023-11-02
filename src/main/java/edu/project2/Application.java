package edu.project2;

import edu.project2.maze.Maze;
import edu.project2.maze.generator.DfsGenerator;
import edu.project2.maze.generator.Generator;
import edu.project2.maze.renderer.CliAnsiRenderer;
import edu.project2.maze.renderer.Renderer;
import edu.project2.maze.solver.BfsSolver;
import edu.project2.maze.solver.Solver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.Optional;
import static edu.project2.maze.Maze.Coordinate;

final class Application {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final int DEFAULT_HEIGHT = 17;
    private static final int DEFAULT_WIDTH = 17;
    private static final Coordinate DEFAULT_START = new Coordinate(1, 1);
    private static final Coordinate DEFAULT_END = new Coordinate(DEFAULT_HEIGHT - 2, DEFAULT_WIDTH - 2);

    static void run() {
        Generator generator = DfsGenerator.getInstance();
        Solver solver = BfsSolver.getInstance();
        Renderer renderer = CliAnsiRenderer.getInstance();

        Maze maze = generator.generate(DEFAULT_HEIGHT, DEFAULT_WIDTH);
        Optional<List<Coordinate>> path = solver.solve(maze, DEFAULT_START, DEFAULT_END);
        if (path.isEmpty()) {
            renderer.render(maze);
        } else {
            renderer.render(maze, path.get());
        }
    }

    private Application() {
    }
}
