package parser.gitCommitsParser;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.gitCommitsParser.converter.Format;

import java.io.IOException;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main (String[] args) {
        Option gitLogFileOption = new Option("l", "gitLogFile", true, "git log file");
        Option regexOption = new Option("r", "regex", true, "regex");
        Option formatOption = new Option("f", "format", true, "format");

        String gitLogFile = "";
        String regex = gitLogFileOption.getValue("(?<text>.+)");
        String format = "";

        Options options = new Options();

        options.addOption(gitLogFileOption);
        options.addOption(regexOption);
        options.addOption(formatOption);

        CommandLineParser parser = new DefaultParser();

        CommandLine commandLine = null;
        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException e) {
            logger.error("Cannot parse this args", e);

            System.exit(1);
        }

//        if (!commandLine.hasOption("l") && !commandLine.hasOption("r")) {
//            for (Option option : options.getOptions()) {
//                System.out.println(option.getOpt() + " " + option.getLongOpt() + ": " + option.getDescription());
//            }
//
//            System.exit(0);
//        }

        if (commandLine.hasOption("l")) {
            gitLogFile = commandLine.getOptionValue(gitLogFileOption);
        }
        if (commandLine.hasOption("r")) {
            regex = commandLine.getOptionValue(regexOption);
        }
        if (commandLine.hasOption("f")) {
            format = commandLine.getOptionValue(formatOption);
        }

        Format formatEnum;
        switch (format) {
            case "json":
                formatEnum = Format.json;
                break;
            case "html":
                formatEnum = Format.html;
                break;
            default:
                formatEnum = Format.plain;
        }

        try {
            new GitCommitsParser("/home/sergey/Desktop/gitLogTest", regex, Format.html).parse();
        } catch (IOException e) {
            logger.error("The file on the " + gitLogFile + " path was not found", e);

            System.exit(2);
        }
    }
}