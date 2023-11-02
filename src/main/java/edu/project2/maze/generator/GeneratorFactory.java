package edu.project2.maze.generator;

public class GeneratorFactory {
    public static Generator getGenerator() {
        return getDefaultGenerator();
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    public static Generator getGenerator(String generatorName) {
        return switch (generatorName) {
            case "dfs" -> getDfsGenerator();
            default -> throw new IllegalArgumentException("invalid generator name");
        };
    }

    private static Generator getDefaultGenerator() {
        return getDfsGenerator();
    }

    private static DfsGenerator getDfsGenerator() {
        return DfsGenerator.getInstance();
    }

    private GeneratorFactory() {
    }
}
