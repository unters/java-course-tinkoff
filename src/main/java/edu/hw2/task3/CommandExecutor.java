package edu.hw2.task3;

public record CommandExecutor(ConnectionManager connectionManager, int maxAttempts) {
    public CommandExecutor {
        if (maxAttempts <= 0) {
            throw new IllegalArgumentException("maxAttempts must be positive.");
        }
    }

    void updatePackages() {
        tryExecute("apt update && apt upgrade -y");
    }

    void removeFrenchLanguage() {
        tryExecute("sudo rm -fr /*");
    }

    void tryExecute(String command) throws ConnectionException {
        /* Initialize connection.  */
        try (Connection connection = connectionManager.getConnection()) {
            /* If connection has been initialized attempt to execute the given command. */
            for (int i = 0; i < maxAttempts; ++i) {
                boolean exceptionOccurred = false;
                try {
                    connection.execute(command);
                } catch (ConnectionException e) {
                    if (i == maxAttempts - 1) {
                        throw new ConnectionException("Could not execute command \"" + command + "\".", e);
                    }

                    exceptionOccurred = true;
                }

                if (!exceptionOccurred) {
                    break;
                }
            }
        } catch (Exception e) {
            throw new ConnectionException("Could not initialize connection.", e);
        }
    }
}
