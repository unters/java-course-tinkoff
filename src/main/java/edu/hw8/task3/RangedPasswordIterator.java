package edu.hw8.task3;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import lombok.NonNull;

public class RangedPasswordIterator implements Iterator<String> {
    private static final String DEFAULT_ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyz";

    private final int passwordLength;
    private final String alphabet;
    private final String firstPassword;
    private final String lastPassword;
    private final Map<Character, Integer> characterIndices = new HashMap<>();

    private boolean noPasswordsLeft = false;
    private StringBuilder nextPassword;

    public static String getDefaultAlphabet() {
        return DEFAULT_ALPHABET;
    }

    public RangedPasswordIterator(String password1, String password2) {
        this(DEFAULT_ALPHABET, password1, password2);
    }

    public RangedPasswordIterator(@NonNull String alphabet, @NonNull String password1, @NonNull String password2) {
        if (password1.length() != password2.length()) {
            throw new IllegalArgumentException("password1 and password2 lengths must match");
        }

        this.alphabet = alphabet;
        for (int i = 0; i < alphabet.length(); ++i) {
            char c = alphabet.charAt(i);
            if (characterIndices.containsKey(c)) {
                throw new IllegalArgumentException("alphabet must contain unique characters only");
            }
            characterIndices.put(c, i);
        }

        for (char c : password1.toCharArray()) {
            if (!characterIndices.containsKey(c)) {
                throw new IllegalArgumentException("password1 contains characters that don't belong to given alphabet");
            }
        }

        for (char c : password2.toCharArray()) {
            if (!characterIndices.containsKey(c)) {
                throw new IllegalArgumentException("password2 contains characters that don't belong to given alphabet");
            }
        }

        this.passwordLength = password1.length();
        firstPassword = password1;
        lastPassword = password2;
        nextPassword = new StringBuilder(firstPassword);
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

    private void generateNextPassword() {
        incrementCharAt(passwordLength - 1);
        if (nextPassword.toString().equals(lastPassword)) {
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
