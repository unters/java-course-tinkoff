package edu.hw4;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Task18 {
    /* Should I use List<List<Animal>> instead of varargs?  */
    @SafeVarargs
    public static Optional<Animal> getHeaviestFishInMultipleLists(List<Animal>... animalsLists) {
        if (animalsLists == null) {
            throw new IllegalArgumentException("animals cannot be null");
        }

        return Arrays.stream(animalsLists)
            .filter(Objects::nonNull)
            .flatMap(Collection::stream)
            .filter(a -> (a.type() == Animal.Type.FISH))
            .max(Comparator.comparing(Animal::weight));
    }

    private Task18() {
    }
}
