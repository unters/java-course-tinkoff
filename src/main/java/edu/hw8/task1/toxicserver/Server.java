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
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 8080;
    private static final int N_THREADS = 3;
    private static final int MAX_CONNECTIONS = 3;

    private final String host;
    private final int port;
    private final int nThreads;
    private final int maxConnections;
    private final Semaphore semaphore;

    public Server() {
        this(DEFAULT_HOST, DEFAULT_PORT, N_THREADS, MAX_CONNECTIONS);
    }

    public Server(String host, int port) {
        this(host, port, N_THREADS, MAX_CONNECTIONS);
    }

    public Server(int nThreads, int maxConnections) {
        this(DEFAULT_HOST, DEFAULT_PORT, nThreads, maxConnections);
    }

    public Server(@NonNull String host, int port, int nThreads, int maxConnections) {
        if (port < 0) {
            throw new IllegalArgumentException("port cannot be negative");
        }

        if (nThreads <= 0) {
            throw new IllegalArgumentException("nThreads must be positive");
        }

        if (maxConnections <= 0) {
            throw new IllegalArgumentException("maxConnections must be positive");
        }

        this.host = host;
        this.port = port;
        this.nThreads = nThreads;
        this.maxConnections = maxConnections;
        semaphore = new Semaphore(maxConnections);
    }

    public void start() {
        try (Selector selector = Selector.open();
             ExecutorService executorService = Executors.newFixedThreadPool(nThreads)) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(host, port));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            LOGGER.info(this.getClass().getName() + ": starting server on port " + port);

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
