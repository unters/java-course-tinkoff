package edu.hw6;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import static edu.hw6.Task2.cloneFile;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task2Test {
    private static final String FOLDER = System.getProperty("user.dir") + "/src/test/resources/edu/hw6/task2";

    @Test
    void cloneFile_NullPathGiven_ThrowNewIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            cloneFile(null);
        });
    }

    @Test
    void cloneFile_PathToFileWithNoCopiesGiven_CopyOneCreated() throws IOException {
        // given
        String testFolderName = "no_copies_test";
        String fileToCopyName = "test.txt";
        initializeNoCopiesTestEnvironment(testFolderName, fileToCopyName);
        Path pathToFile = Paths.get(FOLDER, testFolderName, fileToCopyName);
        List<String> fileContents = Files.readAllLines(pathToFile);

        // when
        cloneFile(pathToFile);
        Path pathToCopy = Paths.get(FOLDER, testFolderName, "test (copy 1).txt");
        List<String> copyContents = Files.readAllLines(pathToCopy);

        // then
        assertThat(Files.exists(pathToCopy)).isTrue();
        assertThat(fileContents).isEqualTo(copyContents);
    }

    @Test
    void cloneFile_PathToFileWithMultipleCopiesGiven_NextCopyCreated() throws IOException {
        // given
        String testFolderName = "multiple_copies_test_1";
        String fileNameNoExtension = "test";
        String extension = ".txt";
        String fileToCopyName = fileNameNoExtension + extension;
        initializeMultipleCopiesTestEnvironment(testFolderName, fileNameNoExtension, extension);
        Path pathToFile = Paths.get(FOLDER, testFolderName, fileToCopyName);
        List<String> fileContents = Files.readAllLines(pathToFile);

        // when
        cloneFile(pathToFile);
        Path pathToCopy = Paths.get(FOLDER, testFolderName, "test (copy 3).txt");
        List<String> copyContents = Files.readAllLines(pathToCopy);

        // then
        assertThat(Files.exists(pathToCopy)).isTrue();
        assertThat(fileContents).isEqualTo(copyContents);
    }

    @Test
    void cloneFile_CreateOfACopyOfAnotherCopy_NextCopyCreated() throws IOException {
        // given
        String testFolderName = "multiple_copies_test_2";
        String fileNameNoExtension = "test";
        String extension = ".txt";
        String fileToCopyName = "test (copy 2).txt";
        initializeMultipleCopiesTestEnvironment(testFolderName, fileNameNoExtension, extension);
        Path pathToFile = Paths.get(FOLDER, testFolderName, fileToCopyName);
        List<String> fileContents = Files.readAllLines(pathToFile);

        // when
        cloneFile(pathToFile);
        Path pathToCopy = Paths.get(FOLDER, testFolderName, "test (copy 3).txt");
        List<String> copyContents = Files.readAllLines(pathToCopy);

        // then
        assertThat(Files.exists(pathToCopy)).isTrue();
        assertThat(fileContents).isEqualTo(copyContents);
    }

    private static void initializeNoCopiesTestEnvironment(String testFolderName, String fileName) throws IOException {
        Path pathToTestFolder = Paths.get(FOLDER, testFolderName);
        if (Files.exists(pathToTestFolder)) {
            deleteDirectory(new File(pathToTestFolder.toString()));
        }
        Files.createDirectory(pathToTestFolder);

        Path pathToFile = Paths.get(pathToTestFolder.toString(), fileName);
        Files.createFile(pathToFile);

        List<String> contents = List.of(
            "На мой взгляд,",                               // Хотя на момент написания этих тестов я ещё
            "Сергей Хватов провел лучшую лекцию в рамках",  // не успел посмотреть лекции по многопоточности.
            "текущего курса по бэкенд-разработке на Жабе."
        );
        Files.write(pathToFile, contents);
    }

    private static void initializeMultipleCopiesTestEnvironment(
        String testFolderName,
        String fileNameNoExtension,
        String extension
    )
        throws IOException {
        Path pathToTestFolder = Paths.get(FOLDER, testFolderName);
        if (Files.exists(pathToTestFolder)) {
            deleteDirectory(new File(pathToTestFolder.toString()));
        }
        Files.createDirectory(pathToTestFolder);

        Path pathToFile = Paths.get(pathToTestFolder.toString(), fileNameNoExtension + extension);
        Files.createFile(pathToFile);
        Path pathToCopyOne = Paths.get(pathToTestFolder.toString(), fileNameNoExtension + " (copy 1)" + extension);
        Files.createFile(pathToCopyOne);
        Path pathToCopyTwo = Paths.get(pathToTestFolder.toString(), fileNameNoExtension + " (copy 2)" + extension);
        Files.createFile(pathToCopyTwo);

        List<String> contents = List.of(
            "Something-something",
            "Lorem",
            "ipsum",
            "42"
        );
        Files.write(pathToFile, contents);
        Files.write(pathToCopyOne, contents);
        Files.write(pathToCopyTwo, contents);
    }

    private static synchronized boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
}
