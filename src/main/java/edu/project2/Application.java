package edu.project2;

import edu.project2.maze.Maze;
import edu.project2.maze.generator.Generator;
import edu.project2.maze.generator.GeneratorFactory;
import edu.project2.maze.renderer.Renderer;
import edu.project2.maze.renderer.RendererFactory;
import edu.project2.maze.solver.Solver;
import edu.project2.maze.solver.SolverFactory;
import java.util.List;
import java.util.Optional;
import static edu.project2.maze.Maze.Coordinate;

final class Application {
    private static final int DEFAULT_HEIGHT = 3;
    private static final int DEFAULT_WIDTH = 31;
    private static final Coordinate DEFAULT_START = new Coordinate(1, 1);
    private static final Coordinate DEFAULT_END = new Coordinate(DEFAULT_HEIGHT - 2, DEFAULT_WIDTH - 2);

    @SuppressWarnings("RegexpSinglelineJava")
    static void run() {
        /* Configure desired generator, solver and renderer.  */
        Generator generator = GeneratorFactory.getGenerator("eller");
        Solver solver = SolverFactory.getSolver();
        Renderer renderer = RendererFactory.getRenderer();

        /* Read desired maze parameters.  */
        int height = DEFAULT_HEIGHT;
        int width = DEFAULT_WIDTH;

        /* Generate and render maze.  */
        Maze maze = generator.generate(height, width);
        renderer.render(maze);
        System.out.println();   // Separation.

        /* Read desired solution parameters.  */
        Coordinate start = DEFAULT_START;
        Coordinate end = DEFAULT_END;

        /* Try to solve and then render.  */
        Optional<List<Coordinate>> path = solver.solve(maze, start, end);
        if (path.isEmpty()) {
            System.out.println("Could not solve the maze.");
        } else {
            renderer.render(maze, path.get());
        }
    }

    private Application() {
    }
}
