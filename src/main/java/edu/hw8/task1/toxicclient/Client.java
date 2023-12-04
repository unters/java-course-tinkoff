package edu.hw8.task1.toxicclient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final int PORT = 8080;
    private static final String HOST = "localhost";
    private static final int BUFFER_SIZE = 512;

    @SuppressWarnings("RegexpSingleLineJava")
    public void run() {
        try (SocketChannel client = SocketChannel.open(new InetSocketAddress(HOST, PORT));
             Scanner scanner = new Scanner(System.in)) {
            LOGGER.info("Connected to server at %s:%d".formatted(HOST, PORT));
            while (true) {
                System.out.print("%s:%d >> ".formatted(HOST, PORT));
                String command = scanner.nextLine();
                switch (command) {
                    case "exit" -> {
                        System.out.println("Exiting...");
                        return;
                    }

                    case "help" -> {
                        System.out.println("""
                            Toxic client commands:
                            <keyword>   Get quote associated with given keyword
                            help        Print this message
                            exit        Terminate the program""");
                    }

                    default -> {
                        ByteBuffer buffer = ByteBuffer.wrap(command.getBytes());
                        client.write(buffer);
                        buffer.clear();

                        ByteBuffer responseBuffer = ByteBuffer.allocate(BUFFER_SIZE);
                        client.read(responseBuffer);
                        String quote = new String(responseBuffer.array()).trim();
                        System.out.println(quote);
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("Unable to connect to the server at %s:%d.".formatted(HOST, PORT));
        }
    }
}
