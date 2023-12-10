package edu.hw9.task2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RecursiveTreeWalkerTest {
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 10, 100, 1_000})
    void findDirectoriesWithAtLeastKFiles_NullDirectoryGiven_ThrowNullPointerException(int k) {
        assertThrows(NullPointerException.class, () -> {
            RecursiveTreeWalker.findDirectoriesWithAtLeastKFiles(null, k);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {-1_000, -121, -42, -1})
    void findDirectoriesWithAtLeastKFiles_InvalidKGiven_ThrowIllegalArgumentException(int k) {
        // given
        Path path = Paths.get("/");

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            // when
            RecursiveTreeWalker.findDirectoriesWithAtLeastKFiles(path, k);
        });
    }

    private static final class FindDirectoriesValidArgumentsProvider implements ArgumentsProvider {
        private static final String USER_DIR = System.getProperty("user.dir");

        private static final Path TEST_DIR_PATH =
            Paths.get(USER_DIR, "src", "test", "resources", "edu", "hw9", "task2");

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                Arguments.of(
                    TEST_DIR_PATH,
                    3,
                    Set.of(
                        TEST_DIR_PATH,
                        Paths.get(TEST_DIR_PATH.toString(), "dir2", "dir3")
                    )
                ),

                Arguments.of(
                    TEST_DIR_PATH,
                    2,
                    Set.of(
                        TEST_DIR_PATH,
                        Paths.get(TEST_DIR_PATH.toString(), "dir1"),
                        Paths.get(TEST_DIR_PATH.toString(), "dir2", "dir3")
                    )
                ),

                Arguments.of(
                    TEST_DIR_PATH,
                    1_000,
                    Set.of()
                )
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(FindDirectoriesValidArgumentsProvider.class)
    void findDirectoriesWithAtLeastKFiles_ValidParametersGiven_ReturnExpectedAnswer(
        Path dir,
        int k,
        Set<Path> expectedAnswer
    ) {
        // when
        Set<Path> resultSet = new HashSet<>(RecursiveTreeWalker.findDirectoriesWithAtLeastKFiles(dir, k));

        // then
        assertThat(resultSet).isEqualTo(expectedAnswer);
    }

    private static final class FindFilesInvalidArgumentsProvider implements ArgumentsProvider {
        private static final Predicate<Path> DUMMY_PREDICATE = path -> true;

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                Arguments.of(null, DUMMY_PREDICATE),
                Arguments.of(Paths.get("/"), null),
                Arguments.of(null, null)
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(FindFilesInvalidArgumentsProvider.class)
    void findMatchingFiles_InvalidArgumentsGiven_ThrowNullPointerException(Path path, Predicate<Path> predicate) {
        assertThrows(NullPointerException.class, () -> {
            RecursiveTreeWalker.findMatchingFiles(path, predicate);
        });
    }

    private static final class FindFilesValidArgumentsProvider implements ArgumentsProvider {
        private static final String USER_DIR = System.getProperty("user.dir");

        private static final Path TEST_DIR_PATH =
            Paths.get(USER_DIR, "src", "test", "resources", "edu", "hw9", "task2");

        private static Predicate<Path> hasExtension(String extension) {
            return (path) -> Files.isRegularFile(path) && path.getFileName().toString().endsWith(extension);
        }

        private static Predicate<Path> isDirectory() {
            return path -> Files.isDirectory(path);
        }

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                Arguments.of(
                    TEST_DIR_PATH,
                    isDirectory(),
                    Set.of(
                        Paths.get(TEST_DIR_PATH.toString(), "dir1"),
                        Paths.get(TEST_DIR_PATH.toString(), "dir2"),
                        Paths.get(TEST_DIR_PATH.toString(), "dir2", "dir3")
                    )),

                Arguments.of(
                    TEST_DIR_PATH,
                    hasExtension(".txt"),
                    Set.of(
                        Paths.get(TEST_DIR_PATH.toString(), "a.txt"),
                        Paths.get(TEST_DIR_PATH.toString(), "b.txt"),
                        Paths.get(TEST_DIR_PATH.toString(), "c.txt"),
                        Paths.get(TEST_DIR_PATH.toString(), "dir2", "dir3", "hello.txt")
                    )
                ),

                Arguments.of(
                    TEST_DIR_PATH,
                    hasExtension(".md"),
                    Set.of(
                        Paths.get(TEST_DIR_PATH.toString(), "dir1", "d.md"),
                        Paths.get(TEST_DIR_PATH.toString(), "dir2", "dir3", "welcome.md")
                    )
                )
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(FindFilesValidArgumentsProvider.class)
    void findMatchingFiles_ValidArgumentsGiven_ReturnExpectedAnswer(
        Path path,
        Predicate<Path> predicate,
        Set<Path> expectedAnswer
    ) {
        // when
        Set<Path> resultSet = new HashSet<>(RecursiveTreeWalker.findMatchingFiles(path, predicate));

        // then
        assertThat(resultSet).isEqualTo(expectedAnswer);
    }
}
