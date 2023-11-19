package edu.project3.logstats.printer;

import edu.project3.logstats.LogsReport;
import java.util.ArrayList;
import java.util.List;
import static edu.project3.Application.SessionParameters;

public final class MarkdownPrinter extends LogsReportPrinter {
    private static final String VERTICAL_SEPARATOR = "|";
    private static final String HORIZONTAL_SEPARATOR = "-";

    public MarkdownPrinter(LogsReport logReport, SessionParameters sessionParameters) {
        super(logReport, sessionParameters);
    }

    @Override
    protected String createHeader(String header) {
        return "### " + header;
    }

    @Override
    protected List<String> createTable(List<String[]> tableCells) {
        List<String> table = new ArrayList<>();
        table.add(createTableRow(tableCells.get(0)));
        table.add(VERTICAL_SEPARATOR + (HORIZONTAL_SEPARATOR + VERTICAL_SEPARATOR).repeat(tableCells.get(0).length));
        for (int i = 1; i < tableCells.size(); ++i) {
            table.add(createTableRow(tableCells.get(i)));
        }
        return table;
    }

    private String createTableRow(String[] rowCells) {
        StringBuilder row = new StringBuilder();
        row.append(VERTICAL_SEPARATOR);
        for (String cell : rowCells) {
            row.append(cell);
            row.append(VERTICAL_SEPARATOR);
        }
        return row.toString();
    }
}
