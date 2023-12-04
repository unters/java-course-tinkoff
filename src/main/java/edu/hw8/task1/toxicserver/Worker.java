package edu.hw8.task1.toxicserver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.Semaphore;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

final class Worker implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final int BUFFER_SIZE = 512;

    private final SocketChannel clientChannel;
    private final Semaphore semaphore;

    Worker(SocketChannel clientChannel, Semaphore semaphore) {
        this.clientChannel = clientChannel;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try (Selector selector = Selector.open()) {
            clientChannel.configureBlocking(false);
            clientChannel.register(selector, SelectionKey.OP_READ);
            while (clientChannel.isOpen()) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                var selectionKeysIterator = selectionKeys.iterator();
                while (selectionKeysIterator.hasNext()) {
                    SelectionKey key = selectionKeysIterator.next();
                    if (!key.isValid()) {
                        LOGGER.warn("Invalid selection key.");
                    } else if (key.isReadable()) {
                        handleReadableKey(key);
                    } else {
                        LOGGER.warn("Unsupported operation status for key: " + key);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.info("Something went wrong. Closing client socket connection.");
        } finally {
            semaphore.release();
        }
    }

    @SneakyThrows(IOException.class)
    private void handleReadableKey(SelectionKey key) {
        LOGGER.info("Readable key received: " + key);
        StringBuffer sb = new StringBuffer();
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        while (clientChannel.read(buffer) > 0) {
            String dataRead = new String(buffer.array());
            if (dataRead.indexOf("\0") != -1) {
                dataRead = dataRead.substring(0, dataRead.indexOf("\0"));
            }
            sb.append(dataRead);
            buffer.clear();
        }

        String keyword = sb.toString();
        LOGGER.info("Quote theme: " + keyword);
        LOGGER.info("Quote found: " + ToxicQuotesBook.getQuoteByKeyword(keyword));

        ByteBuffer responseBuffer =
            ByteBuffer.wrap(ToxicQuotesBook.getQuoteByKeyword(keyword).getBytes());
        while (responseBuffer.hasRemaining()) {
            clientChannel.write(responseBuffer);
        }
    }
}
