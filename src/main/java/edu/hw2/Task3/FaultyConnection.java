package edu.hw2.Task3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class FaultyConnection implements Connection {
    private final static Logger LOGGER = LogManager.getLogger();

    /* Let's assume that an exception will occur on command execution only when commandExecutionFailsLeft counter
     * is non-negative.  */
    private int commandExecutionFailsLeft = 0;

    FaultyConnection() {
        LOGGER.info(getInitializationMessage());
    }

    FaultyConnection(int commandExecutionFailsLeft) {
        if (commandExecutionFailsLeft < 0) {
            throw new IllegalArgumentException("commandExecutionFailsLeft cannot be negative.");
        }

        this.commandExecutionFailsLeft = commandExecutionFailsLeft;
        LOGGER.info(getInitializationMessage());
    }

    @Override
    public void execute(String command) throws ConnectionException {
        if (commandExecutionFailsLeft > 0) {
            --commandExecutionFailsLeft;
            throw new ConnectionException(Integer.toString(commandExecutionFailsLeft));
        }

        LOGGER.info("Command \"" + command + "executed via FaultyConnection");
    }

    @Override
    public void close() {
        LOGGER.info("FaultyConnection closed.");
    }

    private String getInitializationMessage() {
        return "FaultyConnection (" + commandExecutionFailsLeft + ") created.";
    }
}
