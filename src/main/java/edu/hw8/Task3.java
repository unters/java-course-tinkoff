package edu.hw8;

import edu.hw8.task3.MultiThreadedPasswordBruteforcer;
import edu.hw8.task3.SingleThreadedPasswordBruteforcer;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/* On my laptop (2.6GHz, 6 physical cores, 12 logical cores) it takes ~100 seconds to bruteforce passwords of length 5
 * using single threaded bruteforcer vs. ~20 seconds using multithreaded one (using default PasswordIterator and
 * RangedPasswordIterator alphabets).  */

@UtilityClass
@SuppressWarnings("UncommentedMain")
public class Task3 {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final int HASH_RADIX = 16;
    private static final int HASH_LENGTH = 32;
    private static final String HASHING_ALGORITHM = "MD5";

    private static final int NANO_SECONDS_IN_SECOND = 1_000_000_000;

    /* Main method not removed for your convenience.  */
    @SneakyThrows(NoSuchAlgorithmException.class)
    public static void main(String[] args) {
        MessageDigest messageDigest = MessageDigest.getInstance(HASHING_ALGORITHM);
        String password = "qwert";
        String passwordHash = getPasswordHash(password, messageDigest);
        LOGGER.info(password + " : " + passwordHash);

        long multiThreadedBruteforcerStart = System.nanoTime();
        List<String> multiThreadedPotentialPasswords =
            MultiThreadedPasswordBruteforcer.bruteforce(passwordHash, password.length());
        long multiThreadedBruteforcerStop = System.nanoTime();
        for (String potentialPassword : multiThreadedPotentialPasswords) {
            LOGGER.info(potentialPassword);
        }

        long singleThreadedBruteforcerStart = System.nanoTime();
        List<String> singleThreadedPotentialPasswords =
            SingleThreadedPasswordBruteforcer.bruteforce(passwordHash, password.length());
        long singleThreadedBruteforcerStop = System.nanoTime();
        for (String potentialPassword : singleThreadedPotentialPasswords) {
            LOGGER.info(potentialPassword);
        }

        double singleThreadedElapsedTime =
            ((double) singleThreadedBruteforcerStop - singleThreadedBruteforcerStart) / NANO_SECONDS_IN_SECOND;
        double multiThreadedElapsedTime =
            ((double) multiThreadedBruteforcerStop - multiThreadedBruteforcerStart) / NANO_SECONDS_IN_SECOND;
        LOGGER.info("singleThreadedElapsedTime (%.2fs) vs multiThreadedElapsedTime (%.2fs)".formatted(
            singleThreadedElapsedTime,
            multiThreadedElapsedTime
        ));
        LOGGER.info("multi threaded bruteforcer is %.2f times faster than a single threaded one.".formatted(
            singleThreadedElapsedTime / multiThreadedElapsedTime
        ));
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
