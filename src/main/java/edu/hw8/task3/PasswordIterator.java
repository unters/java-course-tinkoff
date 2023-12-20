package edu.hw8.task3;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import lombok.NonNull;

public final class PasswordIterator implements Iterator<String> {
    private static final String DEFAULT_ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyz";

    private final int passwordLength;
    private final String alphabet;
    private final String initialPassword;
    private final Map<Character, Integer> characterIndices = new HashMap<>();

    private boolean noPasswordsLeft = false;
    private StringBuilder nextPassword;

    public PasswordIterator(int passwordLength) {
        this(DEFAULT_ALPHABET, passwordLength);
    }

    public PasswordIterator(@NonNull String alphabet, int passwordLength) {
        if (passwordLength <= 0) {
            throw new IllegalArgumentException("passwordLength must be positive");
        }

        for (int i = 0; i < alphabet.length(); ++i) {
            char c = alphabet.charAt(i);
            if (characterIndices.containsKey(c)) {
                throw new IllegalArgumentException("alphabet must contain unique characters only");
            }
            characterIndices.put(c, i);
        }

        this.alphabet = alphabet;
        this.passwordLength = passwordLength;
        initialPassword = String.valueOf(alphabet.charAt(0)).repeat(passwordLength);
        nextPassword = new StringBuilder(initialPassword);
    }

    @Override
    public boolean hasNext() {
        return !noPasswordsLeft;
    }

    @Override
    public String next() {
        String passwordToReturn = nextPassword.toString();
        generateNextPassword();
        return passwordToReturn;
    }

    public String getDefaultAlphabet() {
        return DEFAULT_ALPHABET;
    }

    private void generateNextPassword() {
        incrementCharAt(passwordLength - 1);
        if (nextPassword.toString().equals(initialPassword)) {
            noPasswordsLeft = true;
        }
    }

    private void incrementCharAt(int index) {
        int characterIndex = characterIndices.get(nextPassword.charAt(index));
        if ((characterIndex + 1) % alphabet.length() == 0) {
            nextPassword.setCharAt(index, alphabet.charAt(0));
            if (index != 0) {
                incrementCharAt(index - 1);
            }
        } else {
            nextPassword.setCharAt(index, alphabet.charAt(characterIndex + 1));
        }
    }
}
