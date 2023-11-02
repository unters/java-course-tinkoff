package edu.hw4;

import java.util.List;

public class Task15 {
    public static int getTotalWeightOfAnimalsWithGivenAge(List<Animal> animals, int minAge, int maxAge) {
        if (animals == null) {
            throw new IllegalArgumentException("animals cannot be null");
        }

        return animals.stream()
            .filter(a -> (a.age() >= minAge && a.age() <= maxAge))
            .map(Animal::weight)
            .mapToInt(w -> w)
            .sum();
    }

    private Task15() {
    }
}
