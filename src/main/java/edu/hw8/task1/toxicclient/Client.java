package edu.hw8.task1.toxicclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int MILLISECONDS_IN_SECOND = 1_000_000;

    private static final int PORT = 8080;
    private static final String HOST = "localhost";
    private static final int BUFFER_SIZE = 512;

    private final InputStream is;
    private final PrintStream ps;

    public Client(InputStream is, PrintStream os) {
        this.is = is;
        this.ps = os;
    }

    @SuppressWarnings("RegexpSingleLineJava")
    public void run() {
//        try (SocketChannel client = SocketChannel.open(new InetSocketAddress(HOST, PORT));
//             Scanner scanner = new Scanner(is);) {
        SocketChannel client = null;
        try (Scanner scanner = new Scanner(is)) {
            while (client == null) {
                try {
                    client = SocketChannel.open(new InetSocketAddress(HOST, PORT));
                } catch (IOException e) {
                    LOGGER.info("Could not establish connection: retrying in one second...");
                    try {
                        Thread.sleep(MILLISECONDS_IN_SECOND);
                    } catch (InterruptedException interruptedException) {
                    }
                }
            }

            LOGGER.info("Connected to server at %s:%d".formatted(HOST, PORT));
            while (true) {
                ps.print("%s:%d >> ".formatted(HOST, PORT));
                String command = scanner.nextLine();
                switch (command) {
                    case "exit" -> {
                        ps.println("Exiting...");
                        return;
                    }

                    case "help" -> {
                        ps.println("""
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
                        ps.println(quote);
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("Unable to connect to the server at %s:%d.".formatted(HOST, PORT));
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                LOGGER.error("Error closing connection: " + e.getMessage());
            }
        }
    }
}
