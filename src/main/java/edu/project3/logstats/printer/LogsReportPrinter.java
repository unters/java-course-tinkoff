package edu.project3.logstats.printer;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import edu.project3.SessionParameters;
import edu.project3.logstats.LogsReport;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class LogsReportPrinter {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final Path CSV_WITH_STATUS_CODES_DESCRIPTIONS =
        Paths.get("src/main/resources/edu/project3/response_codes.csv");
    private static final Map<String, String> STATUS_CODES_DESCRIPTIONS = new HashMap<>();

    static {
        try (Reader reader = Files.newBufferedReader(CSV_WITH_STATUS_CODES_DESCRIPTIONS);
             CSVReader csvReader = new CSVReader(reader)) {
            List<String[]> lines = csvReader.readAll();
            for (int i = 1; i < lines.size(); ++i) {
                try {
                    String[] tokens = lines.get(i);
                    String code = tokens[0];
                    String description = tokens[2];
                    STATUS_CODES_DESCRIPTIONS.put(code, description);
                } catch (Exception e) {
                }
            }
        } catch (IOException | CsvException e) {
            LOGGER.info("Error: could not read %s file.".formatted(CSV_WITH_STATUS_CODES_DESCRIPTIONS.toString()));
        }
    }

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
            new String[] {"Requests count", Long.toString(logReport.getRequestsTotal())},
            new String[] {"Average response size", Long.toString(logReport.getAverageResponseSize()) + "b"},
            new String[] {"Clients count", Long.toString(logReport.getClients().size())}
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
            .map(entry -> new String[] {entry.getKey(), STATUS_CODES_DESCRIPTIONS.getOrDefault(entry.getKey(), "-"),
                entry.getValue().toString()})
            .toList());
        contents.addAll(createTable(statusCodesTableCells));
    }

    protected abstract String createHeader(String header);

    protected abstract List<String> createTable(List<String[]> tableCells);

    protected final LogsReport logReport;
    protected final SessionParameters sessionParameters;

    private final List<String> contents = new ArrayList<>();

    @Getter
    public enum FileFormat {
        MARKDOWN(".md"), ADOC(".adoc");

        FileFormat(String extension) {
            this.extension = extension;
        }

        private final String extension;
    }
}
