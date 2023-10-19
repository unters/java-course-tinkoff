package edu.hw2.task3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public record CommandExecutor(ConnectionManager connectionManager, int maxAttempts) {
    private final static Logger LOGGER = LogManager.getLogger();

    public CommandExecutor {
        if (maxAttempts <= 0) {
            throw new IllegalArgumentException("maxAttempts must be positive.");
        }
    }

    public void updatePackages() {
        tryExecute("apt update && apt upgrade -y");
    }

    public void removeFrenchLanguage() {
        tryExecute("sudo rm -fr /*");
    }

    private void tryExecute(String command) throws ConnectionException {
        boolean connectionEstablished = false;
        boolean commandExecuted = false;
        try (Connection connection = connectionManager.getConnection()) {
            connectionEstablished = true;
            for (int i = 0; i < maxAttempts; ++i) {
                boolean exceptionOccurred = false;
                try {
                    connection.execute(command);
                } catch (ConnectionException e) {
                    exceptionOccurred = true;
                }

                if (!exceptionOccurred) {
                    /* IDE hint tells that the value assigned to `commandExecuted` is never used, but actually it
                     * might be used when connection.close() will throw an exception.  */
                    commandExecuted = true;
                    return;
                }
            }
        } catch (Exception e) {
            if (!connectionEstablished) {
                String exceptionMessage = "Could not establish connection.";
                LOGGER.info(exceptionMessage);
                throw new ConnectionException(exceptionMessage, e);
            } else {
                /* This block of code will be executed when connection.close() will throw an exception. In this case
                 * the acquired resource (established connection) will be lost - there is no information
                 * about it outside the current method (it can't be properly closed).  */
                String exceptionMessage = "Could not close the connection.";
                LOGGER.info(exceptionMessage);
                throw new ConnectionException(exceptionMessage, e);
            }
        }

        String exceptionMessage = "Could not execute command \"" + command + "\".";
        LOGGER.info(exceptionMessage);
        throw new ConnectionException(exceptionMessage);
    }
}
