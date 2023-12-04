package edu.hw8.task1.toxicserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final int PORT = 8080;
    private static final String HOST = "localhost";

    private static final int N_THREADS = 3;
    private static final int MAX_CONNECTIONS = 3;

    private final Semaphore semaphore = new Semaphore(MAX_CONNECTIONS);

    public void start() {
        try (Selector selector = Selector.open();
             ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS)) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(HOST, PORT));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            LOGGER.info(this.getClass().getName() + ": starting server on port " + PORT);

            while (serverSocketChannel.isOpen()) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                var selectionKeysIterator = selectionKeys.iterator();
                while (selectionKeysIterator.hasNext()) {
                    SelectionKey key = selectionKeysIterator.next();
                    if (!key.isValid()) {
                        LOGGER.warn("Invalid selection key.");
                    } else if (key.isAcceptable()) {
                        LOGGER.info("Incomming connection (available connections left: %d)"
                            .formatted(semaphore.availablePermits()));
                        if (semaphore.tryAcquire()) {
                            executorService.execute(new Worker(serverSocketChannel.accept(), semaphore));
                        }
                    } else {
                        LOGGER.warn("Unsupported operation status for key: " + key);
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
