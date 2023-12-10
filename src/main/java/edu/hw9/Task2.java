package edu.hw9;

import edu.hw9.task2.RecursiveTreeWalker;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Task2 {
    private static final int FILES_COUNT = 1_000;

    public static void main(String[] args) {
        List<Path> result =
            RecursiveTreeWalker.findDirectoriesWithAtLeastKFiles(Paths.get("/home/%s".formatted(System.getProperty(
                "user.name"))), FILES_COUNT);
        for (Path path : result) {
            System.out.println(path);
        }
    }
}
