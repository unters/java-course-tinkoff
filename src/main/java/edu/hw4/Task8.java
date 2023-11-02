package edu.hw4;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Task8 {
    public static Optional<Animal> getHeaviestAnimalWithLimitedHeight(List<Animal> animals, int height) {
        if (animals == null) {
            throw new IllegalArgumentException("animals cannot be null");
        }

        return animals.stream()
            .filter((a) -> a.height() < height)
            .max(Comparator.comparingInt(Animal::weight));
    }

    private Task8() {
    }
}
