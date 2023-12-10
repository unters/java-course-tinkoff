package edu.hw6.task1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class DiskMap implements Map<String, String> {
    public static final String STORAGE_FOLDER_NAME = "disk_map_storage";

    private static final Logger LOGGER = LogManager.getLogger();

    public DiskMap(Path path) throws IOException {
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("directory does not exist: " + path);
        }

        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("not a directory: " + path);
        }

        storagePath = Paths.get(path.toString(), STORAGE_FOLDER_NAME);
        storagePathString = storagePath.toString();
        if (!Files.exists(storagePath)) {
            LOGGER.info("Trying to create a directory: " + storagePath);
            /* Initialize new empty storage folder.  */
            Files.createDirectory(storagePath);
        } else {
            LOGGER.info("Directory \"" + storagePath + "\" exists: validating contents.");
            /* Validate and count contents in an existing storage.  */
            try (Stream<Path> fileStream = Files.list(storagePath)) {
                for (Path file : fileStream.toList()) {
                    if (!isValid(file)) {
                        throw new IllegalArgumentException("Invalid storage contents: " + file);
                    }
                    ++size;
                }
            }
            LOGGER.info("\"" + storagePath + "\" contents (" + size + " file" + (size == 1 ? "" : "s")
                + ") validated successfully.");
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public boolean containsKey(Object key) {
        if (key instanceof String s) {
            return Files.exists(Paths.get(storagePath.toString(), s));
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        if (value instanceof String valueString) {
            try (Stream<Path> fileStream = Files.list(storagePath)) {
                for (Path file : fileStream.toList()) {
                    String fileContents = String.join("", Files.readAllLines(file));
                    if (valueString.contentEquals(fileContents)) {
                        return true;
                    }
                }
            } catch (IOException e) {
                /* TODO: add DiskMapException.  */
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    @Override
    public String put(String key, String value) {
        try {
            String previousValue = null;
            Path keyFilePath = Paths.get(storagePathString, key);
            if (Files.exists(keyFilePath)) {
                previousValue = String.join("", Files.readAllLines(keyFilePath));
            } else {
                ++size;
            }

            Files.writeString(keyFilePath, value);
            LOGGER.info("Entry {%s, %s} added (previous value was %s). Current size is %d.".formatted(
                key,
                value,
                (previousValue == null ? "null" : previousValue),
                size
            ));
            return previousValue;
        } catch (IOException e) {
            /* TODO: add DiskMapException.  */
            throw new RuntimeException(e);
        }
    }

    @Override
    public String get(Object key) {
        if (key instanceof String keyString) {
            try {
                Path keyFilePath = Paths.get(storagePathString, keyString);
                if (Files.exists(keyFilePath)) {
                    return String.join("", Files.readAllLines(keyFilePath));
                }
            } catch (IOException e) {
                /* TODO: add DiskMapException.  */
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    @Override
    public String remove(Object key) {
        if (key instanceof String keyString) {
            try {
                Path keyFilePath = Paths.get(storagePathString, keyString);
                String value = null;
                if (Files.exists(keyFilePath)) {
                    value = String.join("", Files.readAllLines(keyFilePath));
                    Files.delete(keyFilePath);
                    --size;
                    LOGGER.info("Entry {%s, %s} removed. Current size is %d.".formatted(keyString, value, size));
                }

                return value;
            } catch (IOException e) {
                /* TODO: add DiskMapException.  */
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> mp) {
        for (var entry : mp.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        try (Stream<Path> fileStream = Files.list(storagePath)) {
            for (Path file : fileStream.toList()) {
                Files.delete(file);
            }

            size = 0;
        } catch (IOException e) {
            /* TODO: add DiskMapException.  */
            throw new RuntimeException(e);
        }
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        try (Stream<Path> fileStream = Files.list(storagePath)) {
            Set<String> keySet = new HashSet<>();
            for (Path filePath : fileStream.toList()) {
                keySet.add(filePath.getFileName().toString());
            }
            return keySet;
        } catch (IOException e) {
            /* TODO: add DiskMapException.  */
            throw new RuntimeException(e);
        }
    }

    @NotNull
    @Override
    public Collection<String> values() {
        try (Stream<Path> fileStream = Files.list(storagePath)) {
            Collection<String> values = new ArrayList<>();
            for (Path filePath : fileStream.toList()) {
                String value = String.join("", Files.readAllLines(filePath));
                values.add(value);
            }
            return values;
        } catch (IOException e) {
            /* TODO: add DiskMapException.  */
            throw new RuntimeException(e);
        }
    }

    @NotNull
    @Override
    public Set<Entry<String, String>> entrySet() {
        try (Stream<Path> fileStream = Files.list(storagePath)) {
            Set<Entry<String, String>> entrySet = new HashSet<>();
            for (Path filePath : fileStream.toList()) {
                String key = filePath.getFileName().toString();
                String value = String.join("", Files.readAllLines(filePath));
                entrySet.add(new AbstractMap.SimpleEntry<>(key, value));
            }
            return entrySet;
        } catch (IOException e) {
            /* TODO: add DiskMapException.  */
            throw new RuntimeException(e);
        }
    }

    private int size = 0;
    private final Path storagePath;
    private final String storagePathString;

    private boolean isValid(Path file) {
        return !Files.isDirectory(file);
    }
}
