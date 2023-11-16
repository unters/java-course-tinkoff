package edu.hw6;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import static edu.hw6.Task4.QUOTE;
import static edu.hw6.Task4.writeQuoteToFile;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task4Test {
    private static final String PROJECT_FOLDER = System.getProperty("user.dir");
    private static final Path FILE_PATH = Paths.get(PROJECT_FOLDER, "src/test/resources/edu/hw6/task4/task4.txt");

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

        Path TASK_FOLDER = Paths.get(HOMEWORK_FOLDER.toString(), "task4");
        if (!Files.exists(TASK_FOLDER)) {
            try {
                Files.createDirectory(TASK_FOLDER);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    void writeQuoteToFile_FilePathGiven_FileContainsQuote() throws IOException {
        // given
        List<String> expectedContents = List.of(QUOTE);

        // when
        writeQuoteToFile(FILE_PATH);
        List<String> actualContents = Files.readAllLines(FILE_PATH);

        // then
        assertThat(actualContents).isEqualTo(expectedContents);
    }
}
