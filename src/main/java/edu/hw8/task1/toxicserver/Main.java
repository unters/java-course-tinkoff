package edu.hw8.task1.toxicserver;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
