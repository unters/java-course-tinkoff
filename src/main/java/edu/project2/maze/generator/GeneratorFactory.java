package edu.project2.maze.generator;

public class GeneratorFactory {
    public static Generator getGenerator() {
        return getDefaultGenerator();
    }

    public static Generator getGenerator(String generatorName) {
        return switch (generatorName) {
            case "dfs" -> getDfsGenerator();
            case "eller" -> getEllerGenerator();
            default -> throw new IllegalArgumentException("invalid generator name");
        };
    }

    private static Generator getDefaultGenerator() {
        return getDfsGenerator();
    }

    private static DfsGenerator getDfsGenerator() {
        return DfsGenerator.getInstance();
    }

    private static EllerGenerator getEllerGenerator() {
        return EllerGenerator.getInstance();
    }

    private GeneratorFactory() {
    }
}
