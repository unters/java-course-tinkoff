package edu.project3.logstats.printer;

import edu.project3.SessionParameters;
import edu.project3.logstats.LogsReport;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

public abstract class LogsReportPrinter {
    public LogsReportPrinter(LogsReport logReport, SessionParameters sessionParameters) {
        this.logReport = logReport;
        this.sessionParameters = sessionParameters;
        generateContents();
    }

    public final void print(Path path)
        throws IOException {
        if (Files.exists(path)) {
            Files.delete(path);
        }
        Files.createFile(path);
        Files.write(path, contents);
    }

    @SuppressWarnings("MultipleStringLiterals")
    protected final void generateContents() {
        contents.add(createHeader("General info"));
        contents.addAll(createTable(List.of(
            new String[] {"Metrics", "Value"},
            new String[] {"File", sessionParameters.logsSource()},
            new String[] {"From", sessionParameters.from().isPresent() ? sessionParameters.from().toString() : "-"},
            new String[] {"To", sessionParameters.to().isPresent() ? sessionParameters.to().toString() : "-"},
            new String[] {"Requests", Long.toString(logReport.getRequestsTotal())},
            new String[] {"Average response size", Long.toString(logReport.getAverageResponseSize())}
        )));

        contents.add(createHeader("Requested resources"));
        List<String[]> requestedResourcesTableCells = new ArrayList<>();
        requestedResourcesTableCells.add(new String[] {"Resource", "Count"});
        requestedResourcesTableCells.addAll(logReport.getRequestsPerResource().entrySet().stream()
            .map(entry -> new String[] {entry.getKey(), entry.getValue().toString()})
            .toList());
        contents.addAll(createTable(requestedResourcesTableCells));

        contents.add(createHeader("Response codes"));
        List<String[]> statusCodesTableCells = new ArrayList<>();
        statusCodesTableCells.add(new String[] {"Code", "Description", "Count"});
        statusCodesTableCells.addAll(logReport.getStatusCodesCount().entrySet().stream()
            .map(entry -> new String[] {entry.getKey(), statusCodesDescriptions.getOrDefault(entry.getKey(), "-"),
                entry.getValue().toString()})
            .toList());
        contents.addAll(createTable(statusCodesTableCells));
    }

    protected abstract String createHeader(String header);

    protected abstract List<String> createTable(List<String[]> tableCells);

    protected final LogsReport logReport;
    protected final SessionParameters sessionParameters;

    private final List<String> contents = new ArrayList<>();
    private final Map<String, String> statusCodesDescriptions = new HashMap<>();    // TODO.

    @Getter
    public enum FileFormat {
        MARKDOWN(".md"), ADOC(".adoc");

        FileFormat(String extension) {
            this.extension = extension;
        }

        private final String extension;
    }
}
