package edu.project2.maze.solver;

public class SolverFactory {
    public static Solver getSolver() {
        return getDefaultSolver();
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    public static Solver getSolver(String solverName) {
        return switch (solverName) {
            case "bfs" -> getBfsSolver();
            default -> throw new IllegalArgumentException("invalid solver name");
        };
    }

    private static Solver getDefaultSolver() {
        return getBfsSolver();
    }

    private static Solver getBfsSolver() {
        return BfsSolver.getInstance();
    }

    private SolverFactory() {
    }
}
