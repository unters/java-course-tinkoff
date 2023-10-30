package edu.hw4;

import java.util.List;

public class Task14 {
    public static boolean isDogWithHeightGreaterOrEqualThanGivenHeight(List<Animal> animals, int height) {
        if (animals == null) {
            throw new IllegalArgumentException("animals cannot be null");
        }

        return !animals.stream()
            .filter(a -> (a.type() == Animal.Type.DOG && a.height() >= height))
            .toList().isEmpty();
    }

    private Task14() {
    }
}
