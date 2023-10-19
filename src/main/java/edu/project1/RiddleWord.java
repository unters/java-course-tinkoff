package edu.project1;

import org.jetbrains.annotations.NotNull;

record RiddleWord(@NotNull String word) {
    RiddleWord {
        if (!wordIsValid(word)) {
            throw new IllegalArgumentException(
                    "word must consist from lowercase english letters only and contain at least 3 different characters.");
        }
    }

    private boolean wordIsValid(String word) {
        boolean[] letterIsPresent = new boolean[26];
        for (char c : word.toCharArray()) {
            if (!Character.isLowerCase(c)) {
                return false;
            }

            letterIsPresent[c - 'a'] = true;
        }

        int differentLettersCount = 0;
        for (int i = 0; i < 26; ++i) {
            if (letterIsPresent[i]) {
                ++differentLettersCount;
            }
        }

        return (differentLettersCount >= 3);
    }
}
