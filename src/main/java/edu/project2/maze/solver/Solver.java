package edu.project2.maze.solver;

import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import java.util.List;
import java.util.Optional;

public interface Solver {
    Optional<List<Coordinate>> solve(Maze maze, Coordinate start, Coordinate end);
}
