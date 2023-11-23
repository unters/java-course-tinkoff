package edu.project3;

import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ApplicationTest {
    private static class ValidArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                Arguments.of((Object) new String[] {
                    "--path=src/test/resources/edu/project3/logs2.txt", "--format=markdown",
                    "--saveto=src/test/resources/edu/project3/logReport.md"
                }),
                Arguments.of((Object) new String[] {
                    "--path=src/test/resources/edu/project3/logs2.txt", "--format=adoc",
                    "--saveto=src/test/resources/edu/project3/logReport.adoc"
                }),
                Arguments.of((Object) new String[] {
                    "--path=%s/src/test/resources/edu/project3/logs2.txt".formatted(System.getProperty("user.dir")),
                    "--format=markdown",
                    "--saveto=%s/src/test/resources/edu/project3/logReport.md".formatted(System.getProperty("user.dir"))
                }),
                Arguments.of((Object) new String[] {
                    "--path=src/test/resources/edu/project3/logs?.txt", "--format=adoc",
                    "--saveto=src/test/resources/edu/project3/logReport.adoc"
                }),
                Arguments.of((Object) new String[] {
                    "--path=%s/src/test/resources/edu/project3/logs?.txt".formatted(System.getProperty("user.dir")),
                    "--format=markdown",
                    "--saveto=src/test/resources/edu/project3/logReport.md"
                }),
                Arguments.of((Object) new String[] {
                    "--path=https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_log"
                        + "s/nginx_logs",
                    "--format=markdown",
                    "--saveto=src/test/resources/edu/project3/logReport.md"
                })
                );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(ValidArgumentsProvider.class)
    void run_ValidArgumentsGiven_NoExceptionIsThrown(String[] args) {
        assertDoesNotThrow(() -> {
            Application.run(args);
        });
    }
}
