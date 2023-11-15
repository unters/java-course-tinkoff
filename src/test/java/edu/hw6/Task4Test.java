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
    private static final Path FILE_PATH =
        Paths.get(System.getProperty("user.dir"), "src/test/resources/edu/hw6/task4/task4.txt");

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
