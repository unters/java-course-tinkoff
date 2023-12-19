package edu.project3.logstats;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LogRecordTest {
    private static final class LogRecordValidArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of("54.77.28.241 - - [24/May/2015:08:05:29 +0000] \"GET /downloads/product_1 HTTP/1.1\" 404 3"
                    + "32 \"-\" \"Debian APT-HTTP/1.3 (1.0.1ubuntu2)\"", "54.77.28.241", "24/May/2015:08:05:29 +0000",
                    "GET", "/downloads/product_1", "404", "332", "-", "Debian APT-HTTP/1.3 (1.0.1ubuntu2)"),
                Arguments.of("217.168.17.5 - - [17/May/2015:08:05:09 +0000] \"GET /downloads/product_2 HTTP/1.1\" 200 4"
                    + "90 \"-\" \"Debian APT-HTTP/1.3 (0.8.10.3)\"", "217.168.17.5", "17/May/2015:08:05:09 +0000",
                    "GET", "/downloads/product_2", "200", "490", "-", "Debian APT-HTTP/1.3 (0.8.10.3)")
            );
        }
    }

    private static final class LogRecordInvalidArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of("Hello world!"),
                Arguments.of("54.77.28.241 - - 2015-05-24T08:05:29 \"GET /downloads/product_1 HTTP/1.1\" 404 332 \"-\" "
                    + "\"Debian APT-HTTP/1.3 (1.0.1ubuntu2)\""),
                Arguments.of("54.77.28.241 - - [24/May/2015:08:05:29 +0000] \"GET /downloads/product_1 HTTP/1.1\" OK 33"
                    + "2 \"-\" \"Debian APT-HTTP/1.3 (1.0.1ubuntu2)\"")
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(LogRecordValidArgumentsProvider.class)
    void new_ValidLogRecordStringGiven_LogRecordContainsExpectedData(
        String logRecordString,
        String ipAddress,
        String dateAndTime,
        String method,
        String url,
        String statusCode,
        String bytesSent,
        String referer,
        String userAgent
    ) {
        // when
        LogRecord logRecord = new LogRecord(logRecordString);

        // then
        assertThat(logRecord.getIpAddress()).isEqualTo(ipAddress);
        assertThat(logRecord.getDateAndTime()).isEqualTo(dateAndTime);
        assertThat(logRecord.getMethod()).isEqualTo(method);
        assertThat(logRecord.getUrl()).isEqualTo(url);
        assertThat(logRecord.getStatusCode()).isEqualTo(statusCode);
        assertThat(logRecord.getBytesSent()).isEqualTo(bytesSent);
        assertThat(logRecord.getReferer()).isEqualTo(referer);
        assertThat(logRecord.getUserAgent()).isEqualTo(userAgent);
    }

    @ParameterizedTest
    @ArgumentsSource(LogRecordInvalidArgumentsProvider.class)
    void new_InvalidLogRecordStringGiven_IllegalArgumentExceptionIsThrown(String logRecordString) {
        assertThrows(IllegalArgumentException.class, () -> new LogRecord(logRecordString));
    }
}
