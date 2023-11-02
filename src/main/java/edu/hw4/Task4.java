package edu.hw4;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Task4 {
    public static Optional<Animal> getAnimalWithTheLongestName(List<Animal> animals) {
        if (animals == null) {
            throw new IllegalArgumentException("animals cannot be null");
        }

        return animals.stream()
            .max(Comparator.comparingInt(animal -> animal.name().length()));
    }

    private Task4() {
    }
}
