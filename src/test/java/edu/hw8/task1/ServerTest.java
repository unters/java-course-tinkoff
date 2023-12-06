package edu.hw8.task1;

import edu.hw8.task1.toxicserver.Server;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServerTest {
    private static class InvalidArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                Arguments.of(-1, 1),
                Arguments.of(1, -1),
                Arguments.of(0, 1),
                Arguments.of(1, 0),
                Arguments.of(0, 0),
                Arguments.of(-1, -1)
            );
        }
    }

    private static class ValidArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                Arguments.of(20, 20),
                Arguments.of(1, 20),
                Arguments.of(20, 1),
                Arguments.of(10, 20),
                Arguments.of(20, 10),
                Arguments.of(1, 1)
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(InvalidArgumentsProvider.class)
    void new_InvalidArgumentGiven_ThrowIllegalArgumentException(int nThreads, int maxConnections) {
        assertThrows(IllegalArgumentException.class, () -> {
            new Server(nThreads, maxConnections);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(ValidArgumentsProvider.class)
    void new_ValidArgumentsGiven_NoExceptionIsThrown(int nThreads, int maxConnections) {
        assertDoesNotThrow(() -> {
            new Server(nThreads, maxConnections);
        });
    }
}
