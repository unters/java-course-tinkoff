package edu.hw2.Task3;

interface FaultyConnectionManager extends ConnectionManager {
    @Override
    FaultyConnection getConnection();
}
