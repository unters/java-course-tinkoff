package edu.hw4;

import java.util.Comparator;
import java.util.List;

public class Task16 {
    public static List<Animal> sortByTypeSexName(List<Animal> animals) {
        if (animals == null) {
            throw new IllegalArgumentException("animals cannot be null");
        }

        return animals.stream()
            .sorted(Comparator.comparing(Animal::name))
            .sorted(Comparator.comparing(Animal::sex))
            .sorted(Comparator.comparing(Animal::type))
            .toList();
    }
}
