package edu.hw2.Task3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StableConnection implements Connection {
    private final static Logger LOGGER = LogManager.getLogger();

    public StableConnection() {
        LOGGER.info("StableConnection created.");
    }

    @Override
    public void execute(String command) {
        LOGGER.info("Command \"" + command + "executed via StableConnection");
    }

    @Override
    public void close() {
        LOGGER.info("StableConnection closed.");
    }
}
