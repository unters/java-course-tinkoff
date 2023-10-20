package edu.project1;

final class LowercaseEnglishLettersSet {
    private static final String ILLEGAL_ARGUMENT_MESSAGE =
        "Illegal argument. `LowercaseEnglishCharactersSet` can store only lowercase english letters.";

    private final boolean[] characterIsPresent = new boolean[26];

    LowercaseEnglishLettersSet() {
    }

    LowercaseEnglishLettersSet(String s) {
        add(s);
    }

    public void add(char c) {
        if (!Character.isLowerCase(c)) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
        }

        characterIsPresent[c - 'a'] = true;
    }

    public void add(String s) {
        for (int i = 0; i < s.length(); ++i) {
            add(s.charAt(i));
        }
    }

    public boolean contains(char c) {
        if (!Character.isLowerCase(c)) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
        }

        return characterIsPresent[c - 'a'];
    }

    public int size() {
        int count = 0;
        for (int i = 0; i < 26; ++i) {
            if (characterIsPresent[i]) {
                ++count;
            }
        }

        return count;
    }
}
