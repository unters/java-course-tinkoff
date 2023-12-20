package edu.hw8.task1;

import edu.hw8.task1.toxicclient.Client;
import edu.hw8.task1.toxicserver.Server;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
public class ServerClientTest {

    @Test
    void interaction_MultipleClientsInteractWithServer_EveryClientGetsServed() {
        // given
        int clientsCount = 20;
        int maxConnections = 6;
        int nThreads = 6;

        // when
        Server server = new Server(maxConnections, nThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        executorService.execute(() -> server.start());
        CountDownLatch countDownLatch = new CountDownLatch(clientsCount);
        for (int i = 0; i < clientsCount; ++i) {
            executorService.execute(() -> runClient(countDownLatch));
        }

        // then
        assertDoesNotThrow(() -> {
            countDownLatch.await();
        });
    }

    void runClient(CountDownLatch countDownLatch) {
        String input = """
            глупый
            личности
            привет
            exit""";
        String expectedOutput = """
            localhost:8080 >> А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма.
            localhost:8080 >> Не переходи на личности там, где их нет
            localhost:8080 >> Бе-бе-бе
            localhost:8080 >> Exiting...
            """;
        InputStream is = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        Client client = new Client(is, ps);
        client.run();
        assertThat(new String(baos.toByteArray())).isEqualTo(expectedOutput);
        countDownLatch.countDown();
    }
}
