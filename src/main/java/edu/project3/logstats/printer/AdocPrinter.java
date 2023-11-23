package edu.project3.logstats.printer;

import edu.project3.SessionParameters;
import edu.project3.logstats.LogsReport;
import java.util.List;

public class AdocPrinter extends LogsReportPrinter {
    public AdocPrinter(LogsReport logReport, SessionParameters sessionParameters) {
        super(logReport, sessionParameters);
    }

    @Override
    protected String createHeader(String header) {
        return null;
    }

    @Override
    protected List<String> createTable(List<String[]> tableCells) {
        return null;
    }
}
