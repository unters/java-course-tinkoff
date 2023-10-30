package edu.hw4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task6 {
    public static Map<Animal.Type, Animal> getHeaviestAnimalsByType(List<Animal> animals) {
        if (animals == null) {
            throw new IllegalArgumentException("animals cannot be null");
        }

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

    private Task6() {
    }
}
