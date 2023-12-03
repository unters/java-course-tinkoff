package edu.hw8.task3;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SingleThreadedPasswordBruteforcer {
    private static final int HASH_RADIX = 16;
    private static final int HASH_LENGTH = 32;
    private static final String HASHING_ALGORITHM = "MD5";

    public static List<String> bruteforce(@NonNull String passwordHash, int minLength, int maxLength) {
        if (minLength <= 0) {
            throw new IllegalArgumentException("minLength must be positive");
        }

        if (minLength >= maxLength) {
            throw new IllegalArgumentException("maxLength must be greater than minLength");
        }

        List<String> potentialPasswords = new ArrayList<>();
        for (int len = minLength; len < maxLength; ++len) {
            potentialPasswords.addAll(bruteforce(passwordHash, len));
        }
        return potentialPasswords;
    }

    @SneakyThrows(NoSuchAlgorithmException.class)
    public static List<String> bruteforce(@NonNull String passwordHash, int expectedLength) {
        if (expectedLength <= 0) {
            throw new IllegalArgumentException("expectedLength must be positive");
        }

        MessageDigest messageDigest = MessageDigest.getInstance(HASHING_ALGORITHM);
        List<String> passwords = new ArrayList<>();
        PasswordIterator it = new PasswordIterator(expectedLength);
        while (it.hasNext()) {
            String nextPassword = it.next();
            if (passwordHash.equals(getPasswordHash(nextPassword, messageDigest))) {
                passwords.add(nextPassword);
            }
        }
        return passwords;
    }

    private static String getPasswordHash(String password, MessageDigest messageDigest) {
        byte[] digest = messageDigest.digest(password.getBytes());
        BigInteger bigInteger = new BigInteger(1, digest);
        String hash = bigInteger.toString(HASH_RADIX);
        /* Add trailing zeroes to ensure length of 32 characters.  */
        while (hash.length() < HASH_LENGTH) {
            hash = "0" + hash;
        }
        return hash;
    }
}
