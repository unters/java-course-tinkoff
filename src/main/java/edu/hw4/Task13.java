package edu.hw4;

import java.util.List;

public class Task13 {
    public static List<Animal> getAnimalsWithComplexNames(List<Animal> animals) {
        if (animals == null) {
            throw new IllegalArgumentException("animals cannot be null");
        }

        return animals.stream()
            .filter(a -> a.name().trim().indexOf(' ') != -1)
            .toList();
    }

    private Task13() {
    }
}
