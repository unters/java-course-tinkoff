package edu.hw4;

import java.util.List;

public class Task10 {
    public static List<Animal> filterAnimalsByAgeAndLegsCountMismatch(List<Animal> animals) {
        if (animals == null) {
            throw new IllegalArgumentException("animals cannot be null");
        }

        return animals.stream()
            .filter(a -> a.age() == a.paws()).toList();
    }

    private Task10() {
    }
}
