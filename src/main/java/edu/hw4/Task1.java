package edu.hw4;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Task1 {
    public static List<Animal> sortByHeightAsc(List<Animal> animals) {
        if (animals == null) {
            throw new IllegalArgumentException("animals cannot be null");
        }

        return animals.stream()
            .sorted(Comparator.comparing(Animal::height))
            .collect(Collectors.toList());
    }

    private Task1() {
    }
}
