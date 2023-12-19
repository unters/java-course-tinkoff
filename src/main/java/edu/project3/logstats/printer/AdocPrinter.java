package edu.project3.logstats.printer;

import edu.project3.SessionParameters;
import edu.project3.logstats.LogsReport;
import java.util.ArrayList;
import java.util.List;

public class AdocPrinter extends LogsReportPrinter {
    private static final String VERTICAL_SEPARATOR = ", ";
    private static final String HORIZONTAL_SEPARATOR = "|===";

    public AdocPrinter(LogsReport logReport, SessionParameters sessionParameters) {
        super(logReport, sessionParameters);
    }

    @Override
    protected String createHeader(String header) {
        return "=== " + header;
    }

    @Override
    protected List<String> createTable(List<String[]> tableCells) {
        List<String> table = new ArrayList<>();
        table.add("[%header, format=csv]");
        table.add(HORIZONTAL_SEPARATOR);
        for (int i = 0; i < tableCells.size(); ++i) {
            table.add(createTableRow(tableCells.get(i)));
        }
        table.add(HORIZONTAL_SEPARATOR);
        return table;
    }

    private String createTableRow(String[] rowCells) {
        StringBuilder row = new StringBuilder();
        row.append(rowCells[0]);
        for (int i = 1; i < rowCells.length; ++i) {
            String cell = rowCells[i];
            row.append(VERTICAL_SEPARATOR);
            row.append(cell);
        }
        return row.toString();
    }
}
