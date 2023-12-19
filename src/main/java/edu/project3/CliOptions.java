package edu.project3;

import lombok.experimental.UtilityClass;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

@UtilityClass
public final class CliOptions {
    private static final Options CLI_OPTIONS = new Options();

    public static Options getOptions() {
        return CLI_OPTIONS;
    }

    static {
        CLI_OPTIONS.addOption(Option.builder(null).longOpt("path")
            .hasArgs()
            .required(true)
            .desc("Path to log file.")
            .build()
        );
        CLI_OPTIONS.addOption(
            null,
            "from",
            true,
            "ISO8601 date (and time) from which logs will be considered in the statistics."
        );
        CLI_OPTIONS.addOption(
            null,
            "to",
            true,
            "ISO8601 date (and time) until which logs will be considered in the statistics."
        );
        CLI_OPTIONS.addOption(null, "format", true, "File format.");
        CLI_OPTIONS.addOption(null, "saveto", true, "Path to file, where log statistics will be saved.");
    }
}
