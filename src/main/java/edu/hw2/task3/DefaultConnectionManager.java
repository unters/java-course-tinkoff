package edu.hw2.task3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultConnectionManager implements ConnectionManager {
    private final static Logger LOGGER = LogManager.getLogger();

    /* Let's assume that a faulty connection will be created only when faultyConnectionsLeft counter is
     * non-negative.  */
    private int faultyConnectionsLeft = 0;

    public DefaultConnectionManager() {
        LOGGER.info(getInitializationMessage());
    }

    public DefaultConnectionManager(int faultyConnectionsLeft) {
        if (faultyConnectionsLeft < 0) {
            throw new IllegalArgumentException("faultyConnectionsLeft cannot be negative.");
        }

        this.faultyConnectionsLeft = faultyConnectionsLeft;
        LOGGER.info(getInitializationMessage());
    }

    @Override
    public Connection getConnection() {
        if (faultyConnectionsLeft > 0) {
            return new FaultyConnection(faultyConnectionsLeft--);
        }

        return new StableConnection();
    }

    private String getInitializationMessage() {
        return "DefaultConnectionManager (" + faultyConnectionsLeft + ") created.";
    }
}
