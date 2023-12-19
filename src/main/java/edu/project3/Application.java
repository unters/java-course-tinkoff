package edu.project3;

import edu.project3.logstats.LogRecord;
import edu.project3.logstats.LogsReport;
import edu.project3.logstats.printer.AdocPrinter;
import edu.project3.logstats.printer.LogsReportPrinter;
import edu.project3.logstats.printer.MarkdownPrinter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("RegexpSinglelineJava")
@UtilityClass
public class Application {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Options CLI_OPTIONS = CliOptions.getOptions();

    private static final String CURRENT_DIR_SYSTEM_PROPERTY = "user.dir";

    @SuppressWarnings("ReturnCount")
    public static void run(String[] args) {
        SessionParameters sessionParameters;
        try {
            sessionParameters = SessionParameters.resolveSessionParameters(args);
            LOGGER.info(sessionParameters);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("logstats", CLI_OPTIONS);
            return;
        }

        LogsReport logsReport;
        try (Stream<LogRecord> logRecordStream = readLogs(sessionParameters)) {
            logsReport = new LogsReport(logRecordStream);
        }

        try {
            LogsReportPrinter logsReportPrinter = switch (sessionParameters.outputFileFormat()) {
                case MARKDOWN -> new MarkdownPrinter(logsReport, sessionParameters);
                case ADOC -> new AdocPrinter(logsReport, sessionParameters);
            };
            logsReportPrinter.print(sessionParameters.logReportFile());
        } catch (IOException e) {
            System.out.println("Error printing log report: " + e.getMessage());
            return;
        }
    }

    @SneakyThrows
    private static Stream<LogRecord> readLogs(SessionParameters sessionParameters) {
        Stream<LogRecord> logRecordStream = switch (sessionParameters.logsSourceType()) {
            case FILE -> {
                Path logsFilePath = Paths.get(sessionParameters.logsSource());
                if (!logsFilePath.isAbsolute()) {
                    logsFilePath = Paths.get(System.getProperty(CURRENT_DIR_SYSTEM_PROPERTY), logsFilePath.toString());
                }

                yield readLogsFromFile(logsFilePath);
            }

            case WILDCARD -> {
                List<Path> logsFiles = resolveWildcardFilePaths(sessionParameters.logsSource());
                yield readLogsFromFiles(logsFiles);
            }
            case URL -> readLogsUsingUrl(sessionParameters.logsSource());
        };

        if (sessionParameters.from().isPresent()) {
            logRecordStream = logRecordStream.filter(logRecord -> {
                LocalDateTime logRecordDateAndTime = logRecord.getLocalDateTime();
                return logRecordDateAndTime.isAfter(sessionParameters.from().get());
            });
        }

        if (sessionParameters.to().isPresent()) {
            logRecordStream = logRecordStream.filter(logRecord -> {
                LocalDateTime logRecordDateAndTime = logRecord.getLocalDateTime();
                return logRecordDateAndTime.isBefore(sessionParameters.to().get());
            });
        }

        return logRecordStream;
    }

    /* Works for linux paths only. Should I add methods to make it easier to add support for other operational
     * systems?  */
    @SneakyThrows
    private static List<Path> resolveWildcardFilePaths(String logsFilesWildcard) {
        int starIndex = logsFilesWildcard.indexOf('*');
        int questionMarkIndex = logsFilesWildcard.indexOf('?');
        int openingBracketIndex = logsFilesWildcard.indexOf('[');
        int minIndexOfSpecialCharacter = Integer.MAX_VALUE;
        if (starIndex != -1) {
            minIndexOfSpecialCharacter = Math.min(minIndexOfSpecialCharacter, starIndex);
        }
        if (questionMarkIndex != -1) {
            minIndexOfSpecialCharacter = Math.min(minIndexOfSpecialCharacter, questionMarkIndex);
        }
        if (openingBracketIndex != -1) {
            minIndexOfSpecialCharacter = Math.min(minIndexOfSpecialCharacter, openingBracketIndex);
        }

        int slashIndex =
            logsFilesWildcard.substring(0, Math.min(logsFilesWildcard.length(), minIndexOfSpecialCharacter))
                .lastIndexOf('/');
        Path directory;
        String pattern;
        if (slashIndex == -1) {
            directory = Paths.get(System.getProperty(CURRENT_DIR_SYSTEM_PROPERTY));
            pattern = logsFilesWildcard;
        } else {
            pattern = logsFilesWildcard.substring(slashIndex + 1);
            directory = Paths.get(logsFilesWildcard.substring(0, slashIndex));
            if (!directory.isAbsolute()) {
                directory = Paths.get(System.getProperty(CURRENT_DIR_SYSTEM_PROPERTY), directory.toString());
            }
        }

        WildcardPathFinder wildcardPathFinder = new WildcardPathFinder(pattern);
        Files.walkFileTree(directory, wildcardPathFinder);
        List<Path> logsFiles = wildcardPathFinder.done();
        for (int i = 0; i < logsFiles.size(); ++i) {
            logsFiles.set(i, directory.resolve(logsFiles.get(i)));
        }
        LOGGER.info("Matching files found: " + Arrays.toString(logsFiles.toArray()));
        return logsFiles;
    }

    @SneakyThrows
    private static Stream<LogRecord> readLogsFromFile(Path logsFile) {
        return Files.lines(logsFile).map(LogRecord::new);
    }

    @SneakyThrows
    private static Stream<LogRecord> readLogsFromFiles(List<Path> logsFiles) {
        List<Stream<LogRecord>> logRecordStreams = new ArrayList<>();
        for (Path logsFile : logsFiles) {
            Stream<LogRecord> logRecordStream = Files.lines((logsFile)).map(LogRecord::new);
            logRecordStreams.add(logRecordStream);
        }

        Stream<LogRecord> resultingLogRecordStream = logRecordStreams.get(0);
        for (int i = 1; i < logRecordStreams.size(); ++i) {
            resultingLogRecordStream = Stream.concat(resultingLogRecordStream, logRecordStreams.get(i));
        }

        return resultingLogRecordStream;
    }

    @SneakyThrows
    private static Stream<LogRecord> readLogsUsingUrl(String urlString) {
        return (new BufferedReader(new InputStreamReader((new URL(urlString)).openStream()))).lines()
            .map(LogRecord::new);
    }

    private static Path getLogsReportPath(SessionParameters sessionParameters) {
        String logsReportFileName =
            "logs_report_" + LocalDate.now() + sessionParameters.outputFileFormat().getExtension();
        return Paths.get(System.getProperty(CURRENT_DIR_SYSTEM_PROPERTY), logsReportFileName);
    }
}
