package edu.hw2.Task3;

public class ConnectionException extends RuntimeException {
    ConnectionException(String message) {
        super(message);
    }

    ConnectionException(String message, Exception cause) {
        super(message, cause);
    }
}
