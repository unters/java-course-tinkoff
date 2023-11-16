package edu.hw6;

import edu.hw6.task1.DiskMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/* DiskMap tests.  */
public class Task1Test {
    private static final String PROJECT_FOLDER = System.getProperty("user.dir");
    private static final String FOLDER =
        Paths.get(PROJECT_FOLDER, "src", "test", "resources", "edu", "hw6", "task1").toString();

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

        Path TASK_FOLDER = Paths.get(HOMEWORK_FOLDER.toString(), "task1");
        if (!Files.exists(TASK_FOLDER)) {
            try {
                Files.createDirectory(TASK_FOLDER);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static final class PutNonExistingKeyArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws IOException {
            return Stream.of(
                Arguments.of(createEmptyDiskMap(), "hello", "there"),
                Arguments.of(createDiskMap1(), "Top", "Gear"),
                Arguments.of(createDiskMap2(), "Doctor", "Kleiner")
            );
        }
    }

    private static final class PutExistingKeyArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws IOException {
            return Stream.of(
                Arguments.of(createDiskMap1(), "Super", "Duper"),
                Arguments.of(createDiskMap2(), "Alyx", "Vance")
            );
        }
    }

    private static final class RemoveKeyThatIsPresentArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws IOException {
            return Stream.of(
                Arguments.of(createDiskMap1(), "Super"),
                Arguments.of(createDiskMap2(), "Gordon")
            );
        }
    }

    private static final class RemoveKeyThatIsNotPresentArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws IOException {
            return Stream.of(
                Arguments.of(createEmptyDiskMap(), "hello"),
                Arguments.of(createDiskMap1(), "Vertex"),
                Arguments.of(createDiskMap2(), "Portal")
            );
        }
    }

    private static final class ClearDiskMapArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws IOException {
            return Stream.of(
                Arguments.of(createEmptyDiskMap()),
                Arguments.of(createDiskMap1()),
                Arguments.of(createDiskMap2())
            );
        }
    }

    @Test
    void new_pathToEmptyDirectoryGiven_NoExceptionIsThrown() throws IOException {
        Path currentTestFolder = Paths.get(FOLDER, "test" + getNextId());
        if (Files.exists(currentTestFolder)) {
            deleteDirectory(new File(currentTestFolder.toString()));
        }

        Files.createDirectory(currentTestFolder);

        assertDoesNotThrow(() -> {
            new DiskMap(currentTestFolder);
        });

        deleteDirectory(new File(currentTestFolder.toString()));
    }

    @Test
    void new_pathToDirectoryWithContentsOfPreviousDiskMapGiven_NoExceptionIsThrown() throws IOException {
        Path currentTestFolder = Paths.get(FOLDER, "test" + getNextId());
        if (Files.exists(currentTestFolder)) {
            deleteDirectory(new File(currentTestFolder.toString()));
        }

        Files.createDirectory(currentTestFolder);

        Path previousDiskMapStorage = Paths.get(currentTestFolder.toString(), DiskMap.STORAGE_FOLDER_NAME);
        Files.createDirectory(previousDiskMapStorage);

        Path pathToPreviousDiskMapEntry = Paths.get(previousDiskMapStorage.toString(), "PreviousKey");
        Files.createFile(pathToPreviousDiskMapEntry);
        Files.writeString(pathToPreviousDiskMapEntry, "PreviousValue");

        assertDoesNotThrow(() -> {
            new DiskMap(currentTestFolder);
        });

        deleteDirectory(new File(currentTestFolder.toString()));
    }

    @ParameterizedTest
    @ArgumentsSource(PutNonExistingKeyArgumentsProvider.class)
    void put_EntryWithNewKeyGiven_DemonstrateExpectedBehaviour(DiskMap diskMap, String key, String value) {
        // given
        int sizeBeforePut = diskMap.size();

        // when
        diskMap.put(key, value);
        int sizeAfterPut = diskMap.size();

        // then
        assertThat(diskMap.isEmpty()).isFalse();                // DiskMap is not empty.
        assertThat(sizeAfterPut).isEqualTo(sizeBeforePut + 1);  // Size increased by one.
        assertThat(diskMap.containsKey(key)).isTrue();          // DiskMap contains given key.
        assertThat(diskMap.containsValue(value)).isTrue();      // DiskMap contains given value.
        assertThat(diskMap.get(key)).isEqualTo(value);          // Given value can be accessed by given key.
    }

    @ParameterizedTest
    @ArgumentsSource(PutExistingKeyArgumentsProvider.class)
    void put_EntryWithExistingKeyGiven_DemonstrateExpectedBehaviour(DiskMap diskMap, String key, String value) {
        // given
        int sizeBeforePut = diskMap.size();

        // when
        diskMap.put(key, value);
        int sizeAfterPut = diskMap.size();

        // then
        assertThat(diskMap.isEmpty()).isFalse();            // DiskMap is not empty.
        assertThat(sizeAfterPut).isEqualTo(sizeBeforePut);  // Size didn't change.
        assertThat(diskMap.containsKey(key)).isTrue();      // DiskMap contains given key.
        assertThat(diskMap.containsValue(value)).isTrue();  // DiskMap contains given value.
        assertThat(diskMap.get(key)).isEqualTo(value);      // Given value can be accessed by given key.
    }

    @ParameterizedTest
    @ArgumentsSource(RemoveKeyThatIsPresentArgumentsProvider.class)
    void remove_KeyThatIsPresentInDiskMapGiven_DemonstrateExpectedBehaviour(DiskMap diskMap, String key) {
        // given
        int sizeBeforeRemove = diskMap.size();

        // when
        diskMap.remove(key);
        int sizeAfterRemove = diskMap.size();

        // then
        assertThat(sizeAfterRemove).isEqualTo(sizeBeforeRemove - 1);    // Size decreased by one.
        assertThat(diskMap.containsKey(key)).isFalse();                 // Corresponding entry removed.
        assertThat(diskMap.get(key)).isNull();
    }

    @ParameterizedTest
    @ArgumentsSource(RemoveKeyThatIsNotPresentArgumentsProvider.class)
    void remove_KeyThatIsNotPresentInDiskMapGiven_SizeNotChanged(DiskMap diskMap, String key) {
        // given
        int sizeBeforeRemove = diskMap.size();
        boolean isEmptyBeforeRemove = diskMap.isEmpty();

        // when
        diskMap.remove(key);
        int sizeAfterRemove = diskMap.size();
        boolean isEmptyAfterRemove = diskMap.isEmpty();

        // then
        assertThat(sizeAfterRemove).isEqualTo(sizeBeforeRemove);        // Size didn't change.
        assertThat(isEmptyAfterRemove).isEqualTo(isEmptyBeforeRemove);  // isEmpty returns the same result.
        assertThat(diskMap.get(key)).isNull();
    }

    @ParameterizedTest
    @ArgumentsSource(ClearDiskMapArgumentsProvider.class)
    void clear_diskMapGiven_DemonstrateExpectedBehaviour(DiskMap diskMap) {
        // given

        // when
        diskMap.clear();

        // then
        assertThat(diskMap.size()).isEqualTo(0);
        assertThat(diskMap.isEmpty()).isTrue();
    }

    private static long id = 1;

    private static synchronized long getNextId() {
        return id++;
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

    private static DiskMap createEmptyDiskMap() throws IOException {
        Path currentTestFolder = Paths.get(FOLDER, "test" + getNextId());
        if (Files.exists(currentTestFolder)) {
            deleteDirectory(new File(currentTestFolder.toString()));
        }

        Files.createDirectory(currentTestFolder);
        return new DiskMap(currentTestFolder);
    }

    private static DiskMap createDiskMap1() throws IOException {
        Path currentTestFolder = Paths.get(FOLDER, "test" + getNextId());
        if (Files.exists(currentTestFolder)) {
            deleteDirectory(new File(currentTestFolder.toString()));
        }

        Files.createDirectory(currentTestFolder);
        DiskMap diskMap = new DiskMap(currentTestFolder);
        diskMap.put("Super", "Secret");
        return diskMap;
    }

    private static DiskMap createDiskMap2() throws IOException {
        Path currentTestFolder = Paths.get(FOLDER, "test" + getNextId());
        if (Files.exists(currentTestFolder)) {
            deleteDirectory(new File(currentTestFolder.toString()));
        }

        Files.createDirectory(currentTestFolder);
        DiskMap diskMap = new DiskMap(currentTestFolder);
        diskMap.put("Ilai", "Vance");
        diskMap.put("Alyx", "Vance");
        diskMap.put("Gordon", "Freeman");
        return diskMap;
    }
}
