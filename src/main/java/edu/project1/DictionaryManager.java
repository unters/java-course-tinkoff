package edu.project1;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/* This class has been added in case I would like to implement some other ways of creating a dictionary (e.g. from
 * file).  */
final class DictionaryManager {
    static Dictionary getDictionary() {
        String demoWords = "book apple kettle experience wheel building paradox limit logarithm";
        try (InputStream inputStream = new ByteArrayInputStream(demoWords.getBytes())) {
            return new Dictionary(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to manage demo input stream.", e);
        }
    }

    private DictionaryManager(){
    }
}
