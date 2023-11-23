package edu.project3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.List;
import java.util.ArrayList;
import static java.nio.file.FileVisitResult.*;

/* https://docs.oracle.com/javase/tutorial/essential/io/find.html  */
public class WildcardPathFinder extends SimpleFileVisitor<Path> {
    private static final Logger LOGGER = LogManager.getLogger();

    private final List<Path> matchingFiles = new ArrayList<>();
    private final PathMatcher matcher;

    WildcardPathFinder(String pattern) {
        matcher = FileSystems.getDefault()
            .getPathMatcher("glob:" + pattern);
    }

    /* Compare the glob pattern against the file or directory name.  */
    void find(Path file) {
        Path name = file.getFileName();
        if (name != null && matcher.matches(name)) {
            matchingFiles.add(name);
        }
    }

    /* Return paths list of matching files.  */
    List<Path> done() {
        return matchingFiles;
    }

    /* Invoke the pattern matching method on each file.  */
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        find(file);
        return CONTINUE;
    }

    /* Invoke the pattern matching method on each directory.  */
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        find(dir);
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        LOGGER.info(exc);
        return CONTINUE;
    }
}
