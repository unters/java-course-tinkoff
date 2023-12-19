package edu.hw10;

import edu.hw10.task2.CacheProxy;
import edu.hw10.testclasses.FibCalculator;
import edu.hw10.testclasses.SimpleFibCalculator;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {
    private static final Path STORAGE =
        Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "edu", "persistence");

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 10, 15, 20, 21, 30, 31, 32})
    void fibCalculator_MethodCalledTwice_AnswersMatch(int n) {
        // given
        FibCalculator fibCalculator = new SimpleFibCalculator();
        FibCalculator proxy = CacheProxy.create(fibCalculator, SimpleFibCalculator.class);

        // when
        int answer = proxy.calculate(n);
        int cachedAnswer = proxy.calculate(n);

        // then
        assertThat(answer).isEqualTo(cachedAnswer);
    }

    @AfterEach
    @SneakyThrows
    void clearDirectory() {
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(STORAGE)) {
            for (Path path : paths) {
                if (path.getFileName().toString().endsWith(".cache")) {
                    Files.delete(path);
                }
            }
        }
    }
}
