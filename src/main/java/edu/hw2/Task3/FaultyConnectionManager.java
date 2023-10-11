package edu.hw2.Task3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class FaultyConnectionManager implements ConnectionManager {
    private final static Logger LOGGER = LogManager.getLogger();

    /* Let's assume that each FaultyConnection will be created with faultyConnectionExecutionFailsLeft
     * initialized to faultyConnectionExecutionFails.  */
    private int faultyConnectionExecutionFails = 0;

    FaultyConnectionManager() {
        LOGGER.info(getInitializationMessage());
    }

    FaultyConnectionManager(int faultyConnectionExecutionFails) {
        if (faultyConnectionExecutionFails < 0) {
            throw new IllegalArgumentException("faultyConnectionExecutionFails cannot be negative.");
        }

        this.faultyConnectionExecutionFails = faultyConnectionExecutionFails;
        LOGGER.info(getInitializationMessage());
    }

    private String getInitializationMessage() {
        return "FaultyConnectionManager (" + faultyConnectionExecutionFails + ") created.";
    }

    @Override
    public FaultyConnection getConnection() {
        return new FaultyConnection(faultyConnectionExecutionFails);
    }
}
