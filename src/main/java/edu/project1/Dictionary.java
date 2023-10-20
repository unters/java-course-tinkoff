package edu.project1;

import org.jetbrains.annotations.NotNull;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

final class Dictionary {
    private static final Random RANDOM = new Random();

    private final List<RiddleWord> words;

    Dictionary(@NotNull InputStream inputStream) {
        words = new ArrayList<>();
        Scanner scanner = new Scanner(inputStream);
        while(scanner.hasNext()) {
            String word = scanner.next();
            words.add(new RiddleWord(word));
        }
    }

    RiddleWord getRandomWord() {
        int randomIndex = RANDOM.nextInt(words.size());
        return words.get(randomIndex);
    }

    List<RiddleWord> getWords() {
        return new ArrayList<>(words);
    }
}
