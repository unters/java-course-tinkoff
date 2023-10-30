package edu.hw4;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Task19 {
    /* This variable is used as a name addition when multiple animals with the same name exist.  */
    private static long id = 1;

    public static Map<String, Set<ValidationError>> getValidationResults(List<Animal> animals) {
        if (animals == null) {
            throw new IllegalArgumentException("animals cannot be null");
        }

        return animals.stream()
            .collect(
                HashMap::new,
                (mp, a) -> {
                    String key = (a.name() == null) ? "Null" : a.name();
                    Set<ValidationError> value = validateAnimal(a);
                    if (!value.isEmpty()) {
                        if (mp.containsKey(key)) {
                            mp.put(key + " " + getNextId(), value);
                        } else {
                            mp.put(key, value);
                        }
                    }
                },
                (Map<String, Set<ValidationError>> mp1, Map<String, Set<ValidationError>> mp2) -> {
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

    private static Set<ValidationError> validateAnimal(Animal animal) {
        Set<ValidationError> validationErrors = new HashSet<>();
        if (animal.name() == null) {
            validationErrors.add(new ValidationError("name is null"));
        }

        if (animal.age() < 0) {
            validationErrors.add(new ValidationError("age is negative"));
        }

        if (animal.height() <= 0) {
            validationErrors.add(new ValidationError("height is non-positive"));
        }

        if (animal.weight() < 0) {
            validationErrors.add(new ValidationError("weight is negative"));
        }

        return validationErrors;
    }

    private static String getNextId() {
       return Long.toString(id++);
    }

    private Task19() {
    }

    public record ValidationError(String message) {
    }
}
