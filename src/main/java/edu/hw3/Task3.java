package edu.hw3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task3 {
    public static <T> Map<T, Integer> buildFrequencyDictionary(List<T> list) {
        if (list == null) {
            throw new NullPointerException("list cannot be null");
        }

        Map<T, Integer> dictionary = new HashMap<>();
        for (var element : list) {
            dictionary.put(element, dictionary.getOrDefault(element, 0) + 1);
        }

        return dictionary;
    }

    private Task3() {
    }
}
