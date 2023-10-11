package edu.hw2.Task3;

interface FaultyConnection extends Connection {
    @Override
    void execute(String command) throws ConnectionException;
}
