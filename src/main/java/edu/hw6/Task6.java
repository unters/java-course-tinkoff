package edu.hw6;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.io.Reader;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings({"MissingSwitchDefault", "MagicNumber"})
public class Task6 {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String LOGGER_OUTPUT_FORMAT = "%-10s%-6s%-10s%-256s";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String PORT_STATUS_OPENED = ANSI_RESET + "OPEN" + ANSI_GREEN + "    ";
    private static final String PORT_STATUS_CLOSED = ANSI_RED + "CLOSED" + ANSI_GREEN + "  ";

    private static final Path CSV_WITH_PORTS_PATH =
        Paths.get(System.getProperty("user.dir"), "src/main/resources/edu/hw6/task6/service-names-port-numbers.csv");
    private static final Map<Integer, String> TCP_PORTS_DESCRIPTIONS = new HashMap<>();
    private static final Map<Integer, String> UDP_PORTS_DESCRIPTIONS = new HashMap<>();

    private static final int MIN_PORT = 1;
    private static final int MAX_PORT = 49151;

    static {
        try (Reader reader = Files.newBufferedReader(CSV_WITH_PORTS_PATH);
             CSVReader csvReader = new CSVReader(reader)) {
            List<String[]> lines = csvReader.readAll();
            for (int i = 1; i < lines.size(); ++i) {
                try {
                    String[] tokens = lines.get(i);
                    int port = Integer.parseInt(tokens[1]);
                    String protocol = tokens[2];
                    String description = tokens[3];
                    switch (protocol) {
                        case "tcp" -> TCP_PORTS_DESCRIPTIONS.put(port, description);
                        case "udp" -> UDP_PORTS_DESCRIPTIONS.put(port, description);
                    }
                } catch (Exception e) {
                }
            }
        } catch (IOException | CsvException e) {
            LOGGER.info("Error: could not read %s file.".formatted(CSV_WITH_PORTS_PATH.toString()));
            throw new RuntimeException(e);
        }
    }

    public static void scanPorts() {
        scanRangeOfPorts(MIN_PORT, MAX_PORT);
    }

    public static void scanRangeOfPorts(int firstPort, int lastPort) {
        LOGGER.info(LOGGER_OUTPUT_FORMAT.formatted("Protocol", "Port", "Status", "Service"));
        for (int port = firstPort; port <= lastPort; ++port) {
            boolean tcpPortOccupied = isPortOccupied(port, Protocol.TCP);
            LOGGER.info(LOGGER_OUTPUT_FORMAT.formatted(
                "TCP",
                port,
                tcpPortOccupied ? PORT_STATUS_CLOSED : PORT_STATUS_OPENED,
                TCP_PORTS_DESCRIPTIONS.getOrDefault(port, "")
            ));
            boolean udpPortOccupied = isPortOccupied(port, Protocol.UDP);
            LOGGER.info(LOGGER_OUTPUT_FORMAT.formatted(
                "UDP",
                port,
                udpPortOccupied ? PORT_STATUS_CLOSED : PORT_STATUS_OPENED,
                UDP_PORTS_DESCRIPTIONS.getOrDefault(port, "")
            ));
        }
    }

    private static boolean isPortOccupied(int port, Protocol protocol) {
        if (protocol == null) {
            throw new IllegalArgumentException("protocol cannot be null");
        }

        if (protocol.equals(Protocol.TCP)) {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                return false;
            } catch (IOException e) {
                return true;
            }
        } else {
            try (DatagramSocket datagramSocket = new DatagramSocket(port)) {
                return false;
            } catch (IOException e) {
                return true;
            }
        }
    }

    private Task6() {
    }

    private enum Protocol {
        TCP, UDP
    }
}
