package edu.hw4;

import java.util.List;

public class Task9 {
    public static long countAnimalsLegs(List<Animal> animals) {
        if (animals == null) {
            throw new IllegalArgumentException("animals cannot be null");
        }

        return animals.stream()
            .map(Animal::paws)
            .mapToLong(a -> a)
            .sum();
    }

    private Task9() {
    }
}
