package edu.hw6;

import edu.hw6.task3.AbstractFilter;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import static edu.hw6.Task3.hasExtension;
import static edu.hw6.Task3.isBiggerThan;
import static edu.hw6.Task3.isDirectory;
import static edu.hw6.Task3.isEmptyDirectory;
import static edu.hw6.Task3.isReadable;
import static edu.hw6.Task3.isSmallerThan;
import static edu.hw6.Task3.isTextFile;
import static edu.hw6.Task3.isWritable;
import static edu.hw6.Task3.regexpContains;
import static edu.hw6.Task3.regexpMatches;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/* There have been some problems with git commit:
 * 1. Non-readable files cannot be committed.
 * 2. Empty directories cannot be committed.
 *
 * Follow these steps to perform all tests on a local machine:
 * 1. Run command: chmod -r src/test/resources/edu/hw6/task3/java_cheat_sheet/08_type_wrappers.md
 * 2. Uncomment code blocks that are commented out as code blocks to be uncommented.
 *
 * There have also been problems with some GitHub tests that depend on read/write rights. After several
 * unsuccessful attempts to fix the problem, I've just commented out these tests. It is strongly
 * recommended perform tests on a local machine (which is probably a sign of bad tests).  */

public class Task3Test {
    private static final String PROJECT_FOLDER = System.getProperty("user.dir");
    private static final Path FOLDER_PATH = Paths.get(PROJECT_FOLDER, "src/test/resources/edu/hw6/task3");

    static {
        Path EDU_FOLDER = Paths.get(PROJECT_FOLDER, "src", "test", "resources", "edu");
        if (!Files.exists(EDU_FOLDER)) {
            try {
                Files.createDirectory(EDU_FOLDER);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Path HOMEWORK_FOLDER = Paths.get(EDU_FOLDER.toString(), "hw6");
        if (!Files.exists(HOMEWORK_FOLDER)) {
            try {
                Files.createDirectory(HOMEWORK_FOLDER);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Path TASK_FOLDER = Paths.get(HOMEWORK_FOLDER.toString(), "task3");
        if (!Files.exists(TASK_FOLDER)) {
            try {
                Files.createDirectory(TASK_FOLDER);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Path EMPTY_TEST_FOLDER = Paths.get(TASK_FOLDER.toString(), "empty_directory");
        if (!Files.exists(EMPTY_TEST_FOLDER)) {
            try {
                Files.createDirectory(EMPTY_TEST_FOLDER);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static final class FilterTestArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(
                    Paths.get(FOLDER_PATH.toString(), "java_cheat_sheet"),
                    isReadable,
                    List.of(
                        Paths.get(FOLDER_PATH.toString(), "java_cheat_sheet", "readme.txt").toString(),
                        Paths.get(FOLDER_PATH.toString(), "java_cheat_sheet", "02_classes.md").toString(),
                        // Line below (08_type_wrappers.md) should be commented out.
                        Paths.get(FOLDER_PATH.toString(), "java_cheat_sheet", "08_type_wrappers.md").toString(),
                        Paths.get(FOLDER_PATH.toString(), "java_cheat_sheet", "14_records.md").toString()
                    )
                ),

                // Uncomment the following block of code.
//                Arguments.of(
//                    Paths.get(FOLDER_PATH.toString(), "java_cheat_sheet"),
//                    isWritable,
//                    List.of(
//                        Paths.get(FOLDER_PATH.toString(), "java_cheat_sheet", "readme.txt").toString(),
//                        Paths.get(FOLDER_PATH.toString(), "java_cheat_sheet", "08_type_wrappers.md").toString(),
//                        Paths.get(FOLDER_PATH.toString(), "java_cheat_sheet", "14_records.md").toString()
//                    )
//                ),

                Arguments.of(
                    FOLDER_PATH,
                    isDirectory,
                    List.of(
                        Paths.get(FOLDER_PATH.toString(), "empty_directory").toString(),
                        Paths.get(FOLDER_PATH.toString(), "java_cheat_sheet").toString()
                    )
                ),

                Arguments.of(
                    FOLDER_PATH,
                    isEmptyDirectory,
                    List.of(
                        Paths.get(FOLDER_PATH.toString(), "empty_directory").toString()
                    )
                ),

                Arguments.of(
                    FOLDER_PATH,
                    isTextFile.and(isWritable).and(isReadable),
                    List.of(
                        Paths.get(FOLDER_PATH.toString(), "secret_info.txt").toString()
                    )
                ),

                Arguments.of(
                    FOLDER_PATH,
                    hasExtension(".md"),
                    List.of(
                        Paths.get(FOLDER_PATH.toString(), "16_string_handling.md").toString()
                    )
                ),

                Arguments.of(
                    Paths.get(FOLDER_PATH.toString(), "java_cheat_sheet"),
                    isBiggerThan(4096).and(hasExtension(".md")),
                    List.of(
                        Paths.get(FOLDER_PATH.toString(), "java_cheat_sheet", "02_classes.md").toString(),
                        Paths.get(FOLDER_PATH.toString(), "java_cheat_sheet", "14_records.md").toString()
                    )
                ),

                Arguments.of(
                    Paths.get(FOLDER_PATH.toString(), "java_cheat_sheet"),
                    isSmallerThan(10240).and(hasExtension(".md")),
                    List.of(
                        Paths.get(FOLDER_PATH.toString(), "java_cheat_sheet", "08_type_wrappers.md").toString(),
                        Paths.get(FOLDER_PATH.toString(), "java_cheat_sheet", "14_records.md").toString()
                    )
                ),

                Arguments.of(
                    FOLDER_PATH,
                    regexpContains("info"),
                    List.of(
                        Paths.get(FOLDER_PATH.toString(), "secret_info.txt").toString()
                    )
                ),

                Arguments.of(
                    FOLDER_PATH,
                    regexpMatches("^.{0,}\\d{1,}\\w{1,}.md$"),
                    List.of(
                        Paths.get(FOLDER_PATH.toString(), "16_string_handling.md").toString()
                    )
                )
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(FilterTestArgumentsProvider.class)
    void filter_DirectoryStreamUsingGivenFilterCreated_ReturnExpectedAnswer(
        Path testFolderPath,
        AbstractFilter filter,
        List<String> expectedAnswer
    ) throws IOException {
        // when
        List<String> actualAnswer = new LinkedList<>();
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(testFolderPath, filter)) {
            entries.forEach(entry -> actualAnswer.add(entry.toString()));
        }

        // then
        actualAnswer.sort(Comparator.naturalOrder());
        List<String> mutableExpectedAnswer = new ArrayList<>(expectedAnswer);
        mutableExpectedAnswer.sort(Comparator.naturalOrder());
        assertThat(actualAnswer).isEqualTo(mutableExpectedAnswer);
    }
}
