package edu.hw4;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Task17 {
    public static boolean doSpidersByteMoreOftenThanDogs(List<Animal> animals) {
        if (animals == null) {
            throw new IllegalArgumentException("animals cannot be null");
        }

        Map<Animal.Type, Integer> mp = animals.stream()
            .filter(a -> (a.type() == Animal.Type.DOG || a.type() == Animal.Type.SPIDER))
            .filter(Animal::bites)
            .collect(Collectors.groupingBy(
                Animal::type,
                Collectors.summingInt(a -> 1)
            ));

        if (mp.size() < 2 || mp.get(Animal.Type.DOG).equals(mp.get(Animal.Type.SPIDER))) {
            return false;
        }

        return mp.get(Animal.Type.DOG).compareTo(mp.get(Animal.Type.SPIDER)) < 0;
    }

    private Task17() {
    }
}
