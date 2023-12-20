package edu.hw8.task1;

import edu.hw8.task1.toxicclient.Client;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClientTest {
    private static class InvalidArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                Arguments.of(null, null),
                Arguments.of(null, new PrintStream(new ByteArrayOutputStream())),
                Arguments.of(new ByteArrayInputStream("exit".getBytes()), null)
            );
        }
    }

    private static class ValidArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                Arguments.of(new ByteArrayInputStream("exit".getBytes()), new PrintStream((new ByteArrayOutputStream())))
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(InvalidArgumentsProvider.class)
    void new_InvalidArgumentGiven_ThrowNullPointerException(InputStream is, PrintStream ps) {
        assertThrows(NullPointerException.class, () -> {
            new Client(is, ps);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(ValidArgumentsProvider.class)
    void new_ValidArgumentsGiven_NoExceptionIsThrown(InputStream is, PrintStream ps) {
        assertDoesNotThrow(() -> {
            new Client(is, ps);
        });
    }
}
