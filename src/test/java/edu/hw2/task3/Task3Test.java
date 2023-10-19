package edu.hw2.task3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task3Test {
    private static final int MAX_ATTEMPTS_DEFAULT = 3;

    @Test
    void tryExecute_DefaultConnectionManagerReturnsStableConnection_NoExceptionIsThrown() {
        ConnectionManager connectionManager = new DefaultConnectionManager();
        CommandExecutor commandExecutor = new CommandExecutor(connectionManager, MAX_ATTEMPTS_DEFAULT);
        assertDoesNotThrow(commandExecutor::updatePackages);
    }

    @Test
    void tryExecute_DefaultConnectionManagerReturnsFaultyConnectionButExecutionSucceeds_NoExceptionIsThrown() {
        ConnectionManager connectionManager = new DefaultConnectionManager(1);
        CommandExecutor commandExecutor = new CommandExecutor(connectionManager, MAX_ATTEMPTS_DEFAULT);
        assertDoesNotThrow(commandExecutor::removeFrenchLanguage);
    }

    @Test
    void tryExecute_ConnectionManagerReturnsFaultyConnectionAndAllAttemptsAreExceeded_ConnectionExceptionIsThrown() {
        ConnectionManager connectionManager = new DefaultConnectionManager(10);
        CommandExecutor commandExecutor = new CommandExecutor(connectionManager, MAX_ATTEMPTS_DEFAULT);
        assertThrows(ConnectionException.class, commandExecutor::updatePackages);
//        var e = assertThrows(ConnectionException.class, commandExecutor::updatePackages);
//        System.out.println(e.getMessage());
//        System.out.println(e.getCause().getMessage());
    }

    @Test
    void tryExecute_ConnectionFromFaultyConnectionManagerButExecutionSuccessfull_NoExceptionIsThrown() {
        ConnectionManager connectionManager = new FaultyConnectionManager(2);
        CommandExecutor commandExecutor = new CommandExecutor(connectionManager, MAX_ATTEMPTS_DEFAULT);
        assertDoesNotThrow(commandExecutor::updatePackages);
    }

    @Test
    void tryExecute_ConnectionFromFaultyConnectionManagerExceedsAllAttempts_ConnectionExceptionIsThrown() {
        ConnectionManager connectionManager = new FaultyConnectionManager(11);
        CommandExecutor commandExecutor = new CommandExecutor(connectionManager, MAX_ATTEMPTS_DEFAULT);
        assertThrows(ConnectionException.class, commandExecutor::updatePackages);
    }
}
