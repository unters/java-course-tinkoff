package edu.hw4;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import static edu.hw4.Animal.Type;
import static edu.hw4.Animal.Sex;

public class Tasks {
    /* Task 1.  */
    public static List<Animal> sortByHeightAsc(List<Animal> animals) {
        return animals.stream()
            .sorted(Comparator.comparing(Animal::height))
            .collect(Collectors.toList());
    }

    /* Task 2.  */
    public static List<Animal> getKHeaviestAnimals(List<Animal> animals, int k) {
        return animals.stream()
            .sorted(Comparator.comparing(Animal::weight).reversed())
            .collect(Collectors.toList())
            .subList(0, Math.min(k, animals.size()));
    }

    /* Task 3.  */
    public static Map<Animal.Type, Integer> countAnimalsByTypes(List<Animal> animals) {
        return animals.stream()
            .collect(Collectors.groupingBy(
                Animal::type,
                Collectors.summingInt(a -> 1)
            ));
    }

    /* Task 4.  */
    public static Optional<Animal> getAnimalWithTheLongestName(List<Animal> animals) {
        return animals.stream()
            .max(Comparator.comparingInt(animal -> animal.name().length()));
    }

    public static Sex getMostFrequentSex(List<Animal> animals) {
        if (animals == null || animals.isEmpty()) {
            return null;
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
}
