package edu.hw4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task20 {
    private static int id = 1;

    public static Map<String, String> getValidationResults(List<Animal> animals) {
        if (animals == null) {
            throw new IllegalArgumentException("animals cannot be null");
        }

        /* If some animals have similar names information about some of them will not be present in resulting Map.  */
        return animals.stream()
            .collect(
                HashMap::new,
                (mp, a) -> {
                    String name = (a.name() == null) ? "null" : a.name();
                    String validityString = validateAnimal(a);
                    if (validityString.isEmpty()) {
                        return;
                    }

                    if (mp.containsKey(name)) {
                        mp.put(name + " " + getNextId(), validityString);
                    } else {
                        mp.put(name, validityString);
                    }
                },
                (mp1, mp2) -> {
                    for (var entry : mp2.entrySet()) {
                        if (mp1.containsKey(entry.getKey())) {
                            mp1.put(entry.getKey() + " " + getNextId(), entry.getValue());
                        } else {
                            mp1.put(entry.getKey(), entry.getValue());
                        }
                    }
                }
            );
    }

    private static String validateAnimal(Animal animal) {
        final String DELIMITER = "; ";
        StringBuilder sb = new StringBuilder();
        if (animal.name() == null) {
            sb.append("name is null");
        }

        if (animal.age() < 0) {
            if (!sb.isEmpty()) {
                sb.append(DELIMITER);
            }
            sb.append("age is negative");
        }

        if (animal.height() <= 0) {
            if (!sb.isEmpty()) {
                sb.append(DELIMITER);
            }
            sb.append("height is non-positive");
        }

        if (animal.weight() < 0) {
            if (!sb.isEmpty()) {
                sb.append(DELIMITER);
            }
            sb.append("weight is negative");
        }

        return sb.toString();
    }

    private static String getNextId() {
        return Long.toString(id++);
    }

    private Task20() {
    }
}
