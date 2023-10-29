package edu.hw4;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import static edu.hw4.Animal.Sex;
import static edu.hw4.Animal.Type;

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

    /* Task 5.  */
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

    /* Task 6.  */
    public static Map<Type, Animal> getHeaviestAnimalsByType(List<Animal> animals) {
        return animals.stream()
            .collect(
                HashMap::new,
                (mp, animal) -> mp.put(animal.type(), animal),
                (mp1, mp2) -> {
                    for (var entry : mp2.entrySet()) {
                        if (mp1.containsKey(entry.getKey())) {
                            if (entry.getValue().weight() > mp1.get(entry.getKey()).weight()) {
                                mp1.put(entry.getKey(), entry.getValue());
                            }
                        } else {
                            mp1.put(entry.getKey(), entry.getValue());
                        }
                    }
                }
            );
    }

    /* Task 7.  */
    public static Animal getKthOldestAnimal(List<Animal> animals, int k) {
        if (k > animals.size()) {
            return null;
        }

        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::age))
            .toList()
            .get(k - 1);
    }

    /* Task 8.  */
    public static Optional<Animal> getHeaviestAnimalWithLimitedHeight(List<Animal> animals, int height) {
        if (animals == null) {
            return Optional.empty();
        }

        return animals.stream()
            .filter((a) -> a.height() < height)
            .max(Comparator.comparingInt(Animal::weight));
    }

    /* Task 9.  */
    public static long countAnimalsLegs(List<Animal> animals) {
        if (animals == null) {
            return -1;
        }

        return animals.stream()
            .map(Animal::paws)
            .mapToLong(a -> a)
            .sum();
    }

    /* Task 10.  */
    public static List<Animal> filterAnimalsByAgeAndLegsCountMismatch(List<Animal> animals) {
        if (animals == null || animals.isEmpty()) {
            return null;
        }

        return animals.stream()
            .filter(a -> a.age() == a.paws()).toList();
    }

    private Tasks() {
    }
}
