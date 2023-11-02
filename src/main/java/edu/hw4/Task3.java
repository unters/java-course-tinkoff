package edu.hw4;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Task3 {
    public static Map<Animal.Type, Integer> countAnimalsByTypes(List<Animal> animals) {
        if (animals == null) {
            throw new IllegalArgumentException("animals cannot be null");
        }

        return animals.stream()
            .collect(Collectors.groupingBy(
                Animal::type,
                Collectors.summingInt(a -> 1)
            ));
    }

    private Task3() {
    }
}
