package edu.hw4;

import java.util.List;

public class Task11 {
    private static final int WEIGHT_LOWER_LIMIT = 100;

    public static List<Animal> getDangerousAnimals(List<Animal> animals) {
        if (animals == null) {
            throw new IllegalArgumentException("animals cannot be null");
        }

        return animals.stream()
            .filter(Animal::bites)
            .filter(a -> a.height() >= WEIGHT_LOWER_LIMIT)
            .toList();
    }

    private Task11() {
    }
}
