package edu.project2.maze.solver;

import edu.project2.maze.Maze;
import java.util.List;
import java.util.Optional;
import static edu.project2.maze.Maze.Coordinate;

public interface Solver {
    Optional<List<Coordinate>> solve(Maze maze);
}
