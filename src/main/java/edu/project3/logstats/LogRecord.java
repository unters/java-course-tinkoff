package edu.project3.logstats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LogRecord {
    private static final String LOG_RECORD_REGEX = "(?<ipaddress>[0-9a-f.:]*) - - \\[(?<dateandtime>\\d{2}\\/[a-zA-Z]{3"
        + "}\\/\\d{4}:\\d{2}:\\d{2}:\\d{2} ([+\\-])\\d{4})] ((\"(?<method>GET|POST|HEAD) )(?<url>.+) (HTTP\\/1\\.1\")) "
        + "(?<statuscode>\\d{3}) (?<bytessent>\\d+) (\"(?<referer>(-)|(.+))\") (\"(?<useragent>"
        + ".+)\")";
    private static final Pattern LOG_RECORD_PATTERN = Pattern.compile(LOG_RECORD_REGEX);

    private static final String LOGS_DATE_AND_TIME_FORMAT = "dd/MMM/yyyy:HH:mm:ss Z";
    private static final DateTimeFormatter LOGS_DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern(LOGS_DATE_AND_TIME_FORMAT);

    public LogRecord(String logRecord) {
        Matcher matcher = LOG_RECORD_PATTERN.matcher(logRecord);
        if (matcher.matches()) {
            ipAddress = matcher.group("ipaddress");
            dateAndTime = matcher.group("dateandtime");
            method = matcher.group("method");
            url = matcher.group("url");
            statusCode = matcher.group("statuscode");
            bytesSent = matcher.group("bytessent");
            referer = matcher.group("referer");
            userAgent = matcher.group("useragent");
        } else {
            throw new IllegalArgumentException("given string is not an nginx log record: " + logRecord);
        }
    }

    public LocalDateTime getLocalDateTime() {
        return LocalDateTime.parse(dateAndTime, LOGS_DATE_TIME_FORMATTER);
    }

    private final String ipAddress;
    private final String dateAndTime;
    private final String method;
    private final String url;
    private final String statusCode;
    private final String bytesSent;
    private final String referer;
    private final String userAgent;
}
