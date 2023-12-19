package edu.project3.logstats;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LogsReportTest {
    private static final class LogRecordStreamArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(
                    Stream.of(
                        "217.168.17.5 - - [17/May/2015:08:05:34 +0000] \"GET /downloads/product_1 HTTP/1.1\" 200 490 \""
                            + "-\" \"Debian APT-HTTP/1.3 (0.8.10.3)\"",
                        "217.168.17.5 - - [17/May/2015:08:05:09 +0000] \"GET /downloads/product_2 HTTP/1.1\" 200 490 \""
                            + "-\" \"Debian APT-HTTP/1.3 (0.8.10.3)\"",
                        "54.77.28.241 - - [24/May/2015:08:05:29 +0000] \"GET /downloads/product_1 HTTP/1.1\" 404 332 \""
                            + "-\" \"Debian APT-HTTP/1.3 (1.0.1ubuntu2)\""
                    ).map(LogRecord::new),
                    3,
                    437,
                    convertListToMap(List.of(
                        new AbstractMap.SimpleEntry<>("/downloads/product_1", 2L),
                        new AbstractMap.SimpleEntry<>("/downloads/product_2", 1L)
                    )),
                    convertListToMap(List.of(
                        new AbstractMap.SimpleEntry<>("200", 2L),
                        new AbstractMap.SimpleEntry<>("404", 1L)
                    ))
                )
            );
        }

        private Map<String, Long> convertListToMap(List<AbstractMap.SimpleEntry<String, Long>> list) {
            Map<String, Long> mp = new HashMap<>();
            for (var entry : list) {
                mp.put(entry.getKey(), entry.getValue());
            }
            return mp;
        }
    }

    @ParameterizedTest
    @ArgumentsSource(LogRecordStreamArgumentsProvider.class)
    void new_LogStreamGiven_ReturnExpectedAnswer(
        Stream<LogRecord> logRecordStream,
        long expectedRequestsTotal,
        long expectedAverageResponseSize,
        Map<String, Long> expectedRequestsPerResource,
        Map<String, Long> expectedStatusCodesCount
    ) {
        // when
        LogsReport logsReport = new LogsReport(logRecordStream);
        logRecordStream.close();

        // then
        assertThat(logsReport.getRequestsTotal()).isEqualTo(expectedRequestsTotal);
        assertThat(logsReport.getAverageResponseSize()).isEqualTo(expectedAverageResponseSize);
        assertThat(logsReport.getRequestsPerResource()).isEqualTo(expectedRequestsPerResource);
        assertThat(logsReport.getStatusCodesCount()).isEqualTo(expectedStatusCodesCount);
    }
}
