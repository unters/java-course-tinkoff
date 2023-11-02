package edu.hw4;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Task2 {
    /* Get at most k heaviest animals.  */
    public static List<Animal> getKHeaviestAnimals(List<Animal> animals, int k) {
        if (animals == null) {
            throw new IllegalArgumentException("animals cannot be null");
        }

        return animals.stream()
            .sorted(Comparator.comparing(Animal::weight).reversed())
            .collect(Collectors.toList())
            .subList(0, Math.min(k, animals.size()));
    }

    private Task2() {
    }
}
