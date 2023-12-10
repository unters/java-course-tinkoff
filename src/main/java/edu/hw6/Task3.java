package edu.hw6;

import edu.hw6.task3.AbstractFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/* That's for me: https://stackoverflow.com/questions/32166471/pass-parameters-to-predicates  */
@SuppressWarnings({"FinalStaticMethod", "ConstantName"})    // Not sure if I should have suppressed ConstantName.
public class Task3 {
    public static final AbstractFilter isReadable = Files::isReadable;

    public static final AbstractFilter isWritable = Files::isWritable;

    public static final AbstractFilter isDirectory = Files::isDirectory;

    public static final AbstractFilter isEmptyDirectory = entry -> {
        if (!isDirectory.accept(entry)) {
            return false;
        }
        try (Stream<Path> fileStream = Files.list(entry)) {
            return fileStream.findAny().isEmpty();
        }
    };

    public static final AbstractFilter isTextFile =
        entry -> (!isDirectory.accept(entry) && entry.getFileName().toString().endsWith(".txt"));

    public static final AbstractFilter hasExtension(String extension) {
        return entry -> (!isDirectory.accept(entry) && entry.getFileName().toString().endsWith(extension));
    }

    public static final AbstractFilter isBiggerThan(long size) {
        return entry -> (Files.size(entry) >= size);
    }

    public static final AbstractFilter isSmallerThan(long size) {
        return entry -> (Files.size(entry) <= size);
    }

    public static final AbstractFilter regexpMatches(String regexp) {
        return entry -> Pattern.compile(regexp).matcher(entry.toString()).matches();
    }

    public static final AbstractFilter regexpContains(String regexp) {
        return entry -> Pattern.compile(regexp).matcher(entry.toString()).find();
    }

    private Task3() {
    }
}
