package edu.hw6;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

public class Task4 {
    public static final String QUOTE = "Programming is learned by writing programs. ― Brian Kernighan";

//    private static final String FOLDER_NAME = System.getProperty("user.dir");

//    public static void main(String[] args) throws IOException {
//        Path pathToFile = Paths.get(FOLDER_NAME, "src/main/resources/edu/hw6/task4/task4.txt");
//        writeQuoteToFile(pathToFile);
//    }

    public static void writeQuoteToFile(Path pathToFile) throws IOException {
        try (OutputStream outputStream = Files.newOutputStream(pathToFile);
             CheckedOutputStream checkedOutputStream = new CheckedOutputStream(outputStream, new CRC32());
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(checkedOutputStream);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(bufferedOutputStream);
             PrintWriter printWriter = new PrintWriter(outputStreamWriter);
        ) {
            printWriter.write(QUOTE);
        }
    }

    private Task4() {
    }
}
