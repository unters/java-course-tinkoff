package edu.project4;

import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@UtilityClass
public class Main {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        LOGGER.debug("Starting Application#run.");
        Application.run(args);
        LOGGER.debug("Application#run executed successfully.");
    }
}
