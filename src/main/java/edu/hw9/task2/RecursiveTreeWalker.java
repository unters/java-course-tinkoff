package edu.hw9.task2;

import lombok.NonNull;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Predicate;

public class RecursiveTreeWalker {
    private static final ForkJoinPool FORK_JOIN_POOL = ForkJoinPool.commonPool();

    public static List<Path> findDirectoriesWithAtLeastKFiles(@NonNull Path dir, int k) {
        if (k < 0) {
            throw new IllegalArgumentException("k cannot be negative");
        }

        List<Path> result = FORK_JOIN_POOL.invoke(new DirectoriesRecursiveTask(dir, k));
        return result;
    }

    public static List<Path> findMatchingFiles(@NonNull Path dir, @NonNull Predicate<Path> predicate) {
        List<Path> result = FORK_JOIN_POOL.invoke(new FilesRecursiveTask(dir, predicate));
        return result;
    }
}
