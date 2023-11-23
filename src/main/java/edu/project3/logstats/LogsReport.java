package edu.project3.logstats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LogsReport {
    public LogsReport(Stream<LogRecord> logRecordStream) {
        /* TODO: make improvements to calculate averageResponseSize correctly.  */
        logRecordStream.forEach(logRecord -> {
            ++requestsTotal;
            responseSizes.add(Double.parseDouble(logRecord.getBytesSent()));
            requestsPerResource.put(logRecord.getUrl(), requestsPerResource.getOrDefault(logRecord.getUrl(), 0L) + 1);
            statusCodesCount.put(
                logRecord.getStatusCode(),
                statusCodesCount.getOrDefault(logRecord.getStatusCode(), 0L) + 1
            );
        });

        for (double responseSize : responseSizes) {
            averageResponseSize += responseSize / requestsTotal;
        }
    }

    private long requestsTotal = 0;
    private double averageResponseSize = 0;
    private List<Double> responseSizes = new ArrayList<>();
    private Map<String, Long> requestsPerResource = new HashMap<>();
    private Map<String, Long> statusCodesCount = new HashMap<>();
}
