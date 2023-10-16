package edu.project1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.util.Arrays;

public final class Main {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) throws IOException {
        GameManager.run();
    }

    private Main() {
    }
}
