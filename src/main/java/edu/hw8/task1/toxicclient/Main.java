package edu.hw8.task1.toxicclient;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        Client client = new Client(System.in, System.out);
        client.run();
    }
}
