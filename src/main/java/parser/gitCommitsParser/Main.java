package parser.gitCommitsParser;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.gitCommitsParser.converter.Format;

import java.io.IOException;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static final String GIT_LOG_FILE_OPTION_DESC = "path to git log file";
    private static final String REGEX_OPTION_DESC = "regex to parse";
    private static final String FORMAT_OPTION_DESC = "format to parse";
    private static final String HTML_TEMPLATE_PATH_OPTION_DESC = "HTML template path (Thymeleaf)";

    private static final Option gitLogFileOption = new Option("l", "gitLogFile", true, GIT_LOG_FILE_OPTION_DESC);
    private static final Option regexOption = new Option("r", "regex", true, REGEX_OPTION_DESC);
    private static final Option formatOption = new Option("f", "format", true, FORMAT_OPTION_DESC);
    private static final Option HTMLTemplatePathOption = new Option("h", "htmlTemplate", true, HTML_TEMPLATE_PATH_OPTION_DESC);

    private static String gitLogFile = "";
    private static String regex = gitLogFileOption.getValue("(?<text>.+)");
    private static Format format;
    private static String HTMLTemplatePath = "";
    public static void main (String[] args) {
        Options options = getOptions();

        CommandLine commandLine = getCommandLine(options, args);

        if (!commandLine.hasOption("l")) {
            for (Option option : options.getOptions()) {
                System.out.println(option);
            }

            System.exit(0);
        }

        getOptionValuesFromCommandLine(commandLine);

        try {
            GitCommitsParser parser = new GitCommitsParser(gitLogFile, regex, format, HTMLTemplatePath);

            System.out.println(parser.parse());
        } catch (IOException e) {
            logger.error("The file on the " + gitLogFile + " path was not found", e);

            System.exit(2);
        }
    }

    private static Options getOptions() {
        Options options = new Options();

        options.addOption(gitLogFileOption);
        options.addOption(regexOption);
        options.addOption(formatOption);
        options.addOption(HTMLTemplatePathOption);

        return options;
    }

    private static CommandLine getCommandLine(Options options, String[] args) {
        CommandLineParser commandLineParser = new DefaultParser();

        CommandLine commandLine = null;
        try {
            commandLine = commandLineParser.parse(options, args);
        } catch (ParseException e) {
            logger.error("Cannot parse this args", e);

            System.exit(1);
        }

        return commandLine;
    }

    private static void getOptionValuesFromCommandLine(CommandLine commandLine) {
        if (commandLine.hasOption("l")) {
            gitLogFile = commandLine.getOptionValue(gitLogFileOption);
        }
        if (commandLine.hasOption("r")) {
            regex = commandLine.getOptionValue(regexOption);
        }
        if (commandLine.hasOption("f")) {
            String formatFromCommandLine = commandLine.getOptionValue(formatOption);

            switch (formatFromCommandLine) {
                case "json":
                    format = Format.JSON;
                    break;
                case "html":
                    format = Format.HTML;
                    break;
                default:
                    format = Format.PLAIN;
            }
        }
        if (commandLine.hasOption("h")) {
            HTMLTemplatePath = commandLine.getOptionValue(HTMLTemplatePathOption);
        }
    }
}