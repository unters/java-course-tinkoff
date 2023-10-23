package edu.hw3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class Task3 {
    public static <T> Map<T, Integer> buildFrequencyDictionary(@NotNull List<T> list) {
        Map<T, Integer> dictionary = new HashMap<>();
        for (var element : list) {
            dictionary.put(element, dictionary.getOrDefault(element, 0) + 1);
        }

        return dictionary;
    }

    private Task3() {
    }
}
