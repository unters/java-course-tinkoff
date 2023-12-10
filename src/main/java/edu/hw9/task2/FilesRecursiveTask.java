package edu.hw9.task2;

import lombok.SneakyThrows;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;

public class FilesRecursiveTask extends RecursiveTask<List<Path>> {
    private final Path dir;
    private final Predicate<Path> predicate;

    FilesRecursiveTask(Path dir, Predicate<Path> predicate) {
        this.dir = dir;
        this.predicate = predicate;
    }

    @Override
    @SneakyThrows(IOException.class)
    protected List<Path> compute() {
        List<Path> result = new ArrayList<>();
        List<FilesRecursiveTask> forks = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(dir)) {
            for (Path path : directoryStream) {
                if (Files.isDirectory(path)) {
                    FilesRecursiveTask filesRecursiveTask = new FilesRecursiveTask(path, predicate);
                    forks.add(filesRecursiveTask);
                    filesRecursiveTask.fork();
                }

                if (predicate.test(path)) {
                    result.add(path);
                }
            }
        }

        for (FilesRecursiveTask filesRecursiveTask : forks) {
            result.addAll(filesRecursiveTask.join());
        }

        return result;
    }
}
