package edu.project2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

final class Application {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final int DEFAULT_HEIGHT = 32;
    private static final int DEFAULT_WIDTH = 32;

    /* Parameters.  */
    private static int height = DEFAULT_HEIGHT;
    private static int width = DEFAULT_WIDTH;

    static void run() {
    }

    private Application() {
    }
}
