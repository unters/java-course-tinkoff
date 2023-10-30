package edu.hw4;

import java.util.Comparator;
import java.util.List;

public class Task7 {
    public static Animal getKthOldestAnimal(List<Animal> animals, int k) {
        if (animals == null) {
            throw new IllegalArgumentException("animals cannot be null");
        }

        if (k > animals.size()) {
            throw new IllegalArgumentException("k cannot be grater than animals.size()");
        }

        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::age))
            .toList()
            .get(k - 1);
    }

    private Task7() {
    }
}
