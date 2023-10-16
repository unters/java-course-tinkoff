package edu.project1;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

final class Dictionary {
    private static final String WORD_REGEX = "[a-z]{3,}";
    private static final Random RANDOM = new Random();

    private final List<String> words;

    Dictionary(String filepath) throws IOException {
        words = new ArrayList<>();
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(filepath)) {
            assert in != null;
            Scanner scanner = new Scanner(in);
            while(scanner.hasNext()) {
                String word = scanner.next();
                if (!Pattern.matches(WORD_REGEX, word)) {
                    throw new IOException("Given file must contain words of length greater than 2 and consisting " +
                        "only from lowercase english characters.");
                }

                words.add(word);
            }
        }
    }

    String getRandomWord() {
        int randomIndex = RANDOM.nextInt(words.size());
        return words.get(randomIndex);
    }

    List<String> getWords() {
        return new ArrayList<>(words);
    }
}
