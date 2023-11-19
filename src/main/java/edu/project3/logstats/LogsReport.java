package edu.project3.logstats;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LogsReport {
    public void update(Stream<LogRecord> logRecordStream) {
        /* TODO: make improvements to calculate averageResponseSize correctly.  */
        logRecordStream.forEach(logRecord -> {
            ++requestsTotal;
            averageResponseSize += Long.parseLong(logRecord.getBytesSent());
            requestsPerResource.put(logRecord.getUrl(), requestsPerResource.getOrDefault(logRecord.getUrl(), 0L) + 1);
            statusCodesCount.put(
                logRecord.getStatusCode(),
                statusCodesCount.getOrDefault(logRecord.getStatusCode(), 0L) + 1
            );
        });

        averageResponseSize /= requestsTotal;
    }

    private long requestsTotal = 0;
    private long averageResponseSize = 0;
    private Map<String, Long> requestsPerResource = new HashMap<>();
    private Map<String, Long> statusCodesCount = new HashMap<>();
}
