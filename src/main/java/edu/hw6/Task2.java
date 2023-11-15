package edu.hw6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Task2 {
    private static final String COPY_SUFFIX_REGEXP = " \\(copy \\d\\)";

    public static void cloneFile(Path pathToFile) throws IOException {
        if (pathToFile == null) {
            throw new IllegalArgumentException("path cannot be null");
        }

        if (Files.isDirectory(pathToFile)) {
            throw new IllegalArgumentException("is a directory: " + pathToFile.toString());
        }

        Path pathToFileCopy = generatePathToFileCopy(pathToFile);
        Files.createFile(pathToFileCopy);
        List<String> contents = Files.readAllLines(pathToFile);
        Files.write(pathToFileCopy, contents);
    }

    private static Path generatePathToFileCopy(Path pathToFile) throws IOException {
        String directory = pathToFile.getParent().toString();
        String fileName = pathToFile.getFileName().toString();

        int fileExtensionIndex = fileName.indexOf(".");
        String fileNameNoExtension = (fileExtensionIndex == -1) ? fileName : fileName.substring(0, fileExtensionIndex);
        String extension = (fileExtensionIndex == -1) ? "" : fileName.substring(fileExtensionIndex);

        /* Given file might already be a copy of some another file.  */
        Matcher matcher = Pattern.compile(COPY_SUFFIX_REGEXP + "$").matcher(fileNameNoExtension);
        String fileNameNoCopySuffixNoExtension = matcher.find()
            ? fileNameNoExtension.substring(0, fileNameNoExtension.lastIndexOf('(') - 1)
            : fileNameNoExtension;
        Pattern fileCopyPattern = Pattern.compile(fileNameNoCopySuffixNoExtension + COPY_SUFFIX_REGEXP + extension);

        /* Check if there already are any copies of the given file in the file directory. If there are, find the
         * largest copy index among all the copies.  */
        int lastCopyIndex = 0;
        try (Stream<Path> fileStream = Files.list(Paths.get(directory))) {
            for (Path path : fileStream.toList()) {
                String nextFileName = path.getFileName().toString();
                if (!Files.isDirectory(path) && fileCopyPattern.matcher(nextFileName).matches()) {
                    int nextFileExtensionIndex = nextFileName.indexOf(".");
                    String nextFileNameNoExtension = (nextFileExtensionIndex == -1)
                        ? nextFileName
                        : nextFileName.substring(0, nextFileExtensionIndex);
                    int copyIndex = Integer.parseInt(nextFileNameNoExtension.substring(
                        nextFileName.lastIndexOf(" ") + 1,
                        nextFileName.lastIndexOf(")")
                    ));
                    lastCopyIndex = Math.max(lastCopyIndex, copyIndex);
                }
            }
        }

        String copyNameNoExtension = fileNameNoCopySuffixNoExtension + " (copy %d)".formatted(lastCopyIndex + 1);
        return Paths.get(directory, copyNameNoExtension + extension);
    }

    private Task2() {
    }
}
