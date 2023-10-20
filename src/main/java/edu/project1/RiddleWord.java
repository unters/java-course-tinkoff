package edu.project1;

import org.jetbrains.annotations.NotNull;

record RiddleWord(@NotNull String word) {
    private static final int MIN_NUMBER_OF_DIFFERENT_LETTERS = 3;

    RiddleWord {
        if (!wordIsValid(word)) {
            throw new IllegalArgumentException(
                "word must consist from lowercase english letters only and contain at least 3 different characters.");
        }
    }

    private boolean wordIsValid(String word) {
        LowercaseEnglishLettersSet lettersSet;
        try {
            lettersSet = new LowercaseEnglishLettersSet(word);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return (lettersSet.size() >= MIN_NUMBER_OF_DIFFERENT_LETTERS);
    }
}
