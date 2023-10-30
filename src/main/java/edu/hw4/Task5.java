package edu.hw4;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Task5 {
    public static Animal.Sex getMostFrequentSex(List<Animal> animals) {
        if (animals == null) {
            throw new IllegalArgumentException("animals cannot be null");
        }

        if (animals.isEmpty()) {
            throw new IllegalArgumentException("animals cannot be empty");
        }

        return animals.stream()
            .collect(Collectors.groupingBy(
                Animal::sex,
                Collectors.summingInt(a -> 1)
            ))
            .entrySet()
            .stream()
            .max(Comparator.comparingInt(Map.Entry::getValue))
            .get().getKey();
    }

    private Task5() {
    }
}
