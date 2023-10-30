package edu.hw4;

import java.util.List;

public class Task12 {
    public static long getNumberOfAnimalsWithWeightGreaterThanHeight(List<Animal> animals) {
        if (animals == null) {
            throw new IllegalArgumentException("animals cannot be null");
        }

        return animals.stream()
            .filter(a -> a.weight() > a.height())
            .count();
    }

    private Task12() {
    }
}
