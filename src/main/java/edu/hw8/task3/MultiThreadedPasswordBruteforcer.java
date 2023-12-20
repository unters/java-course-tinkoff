package edu.hw8.task3;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class MultiThreadedPasswordBruteforcer {
    private static final int HASH_RADIX = 16;
    private static final int HASH_LENGTH = 32;
    private static final String HASHING_ALGORITHM = "MD5";

    private static final int N_THREADS = 6;

    public static List<String> bruteforce(@NotNull String passwordHash, int minLength, int maxLength) {
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

    @SneakyThrows({ExecutionException.class, InterruptedException.class})
    public static List<String> bruteforce(@NonNull String passwordHash, int expectedLength) {
        if (expectedLength <= 0) {
            throw new IllegalArgumentException("expectedLength must be positive");
        }

        String alphabet = RangedPasswordIterator.getDefaultAlphabet();
        int nThreads = Math.min(N_THREADS, alphabet.length());
        List<String> rangeLimits = new ArrayList<>();
        int step = alphabet.length() / nThreads;
        for (int i = 0; i < nThreads; ++i) {
            rangeLimits.add(String.valueOf((alphabet.charAt(i * step))).repeat(expectedLength));
        }
        rangeLimits.add(String.valueOf((alphabet.charAt(0))).repeat(expectedLength));

        try (ExecutorService executorService = Executors.newFixedThreadPool(nThreads)) {
            List<Future<List<String>>> futuresList = new ArrayList<>();
            for (int i = 0; i < nThreads; ++i) {
                String password1 = rangeLimits.get(i);
                String password2 = rangeLimits.get(i + 1);
                futuresList.add(executorService.submit(() -> bruteforceRanged(passwordHash, password1, password2)));
            }

            List<String> possiblePasswords = new ArrayList<>();
            for (Future<List<String>> future : futuresList) {
                while (!future.isDone()) {
                }
                possiblePasswords.addAll(future.get());
            }

            return possiblePasswords;
        }
    }

    @SneakyThrows(NoSuchAlgorithmException.class)
    private static List<String> bruteforceRanged(String passwordHash, String password1, String password2) {
        List<String> passwords = new ArrayList<>();
        MessageDigest messageDigest = MessageDigest.getInstance(HASHING_ALGORITHM);
        RangedPasswordIterator it = new RangedPasswordIterator(password1, password2);
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
