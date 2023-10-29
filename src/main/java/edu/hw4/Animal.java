package edu.hw4;

public record Animal(
    String name,
    Type type,
    Sex sex,
    int age,
    int height,
    int weight,
    boolean bites
) {
    private static final int MAMMAL_LEGS_NUMBER = 4;
    private static final int SPIDER_LEGS_NUMBER = 8;
    private static final int BIRD_LEGS_NUMBER = 2;
    private static final int FISH_LEGS_NUMBER = 0;

    public enum Type {
        CAT, DOG, BIRD, FISH, SPIDER
    }

    public enum Sex {
        M, F
    }

    public int paws() {
        return switch (type) {
            case CAT, DOG -> MAMMAL_LEGS_NUMBER;
            case BIRD -> BIRD_LEGS_NUMBER;
            case FISH -> FISH_LEGS_NUMBER;
            case SPIDER -> SPIDER_LEGS_NUMBER;
        };
    }
}
