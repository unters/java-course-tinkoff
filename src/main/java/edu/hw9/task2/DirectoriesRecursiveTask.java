package edu.hw9.task2;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import lombok.SneakyThrows;

class DirectoriesRecursiveTask extends RecursiveTask<List<Path>> {
    private final Path dir;
    private final int k;

    DirectoriesRecursiveTask(Path dir, int k) {
        this.dir = dir;
        this.k = k;
    }

    @Override
    @SneakyThrows(IOException.class)
    protected List<Path> compute() {
        List<Path> result = new ArrayList<>();
        List<DirectoriesRecursiveTask> forks = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(dir)) {
            int filesCount = 0;
            for (Path path : directoryStream) {
                if (Files.isDirectory(path) && Files.isReadable(path)) {    /* Check reading permissions too.  */
                    DirectoriesRecursiveTask directoriesRecursiveTask = new DirectoriesRecursiveTask(path, k);
                    forks.add(directoriesRecursiveTask);
                    directoriesRecursiveTask.fork();
                } else {
                    ++filesCount;
                }
            }

            if (filesCount >= k) {
                result.add(dir);
            }
        }

        for (DirectoriesRecursiveTask directoriesRecursiveTask : forks) {
            result.addAll(directoriesRecursiveTask.join());
        }

        return result;
    }
}
