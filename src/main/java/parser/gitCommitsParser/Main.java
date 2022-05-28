package parser.gitCommitsParser;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.gitCommitsParser.converter.Format;

import java.io.IOException;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main (String[] args) {
        String gitLogFileOptionDesc = "path to git log file";
        String regexOptionDesc = "regex to parse";
        String formatOptionDesc = "format to parse";
        String HTMLTemplatePathOptionDesc = "HTML template path (Thymeleaf)";

        Option gitLogFileOption = new Option("l", "gitLogFile", true, gitLogFileOptionDesc);
        Option regexOption = new Option("r", "regex", true, regexOptionDesc);
        Option formatOption = new Option("f", "format", true, formatOptionDesc);
        Option HTMLTemplatePathOption = new Option("h", "htmlTemplate", true, HTMLTemplatePathOptionDesc);

        String gitLogFile = "";
        String regex = gitLogFileOption.getValue("(?<text>.+)");
        String format = "";
        String HTMLTemplatePath = "";

        Options options = new Options();

        options.addOption(gitLogFileOption);
        options.addOption(regexOption);
        options.addOption(formatOption);
        options.addOption(HTMLTemplatePathOption);

        CommandLineParser commandLineParser = new DefaultParser();

        CommandLine commandLine = null;
        try {
            commandLine = commandLineParser.parse(options, args);
        } catch (ParseException e) {
            logger.error("Cannot parse this args", e);

            System.exit(1);
        }

        if (!commandLine.hasOption("l")) {
            for (Option option : options.getOptions()) {
                System.out.println(option);
            }

            System.exit(0);
        }

        if (commandLine.hasOption("l")) {
            gitLogFile = commandLine.getOptionValue(gitLogFileOption);
        }
        if (commandLine.hasOption("r")) {
            regex = commandLine.getOptionValue(regexOption);
        }
        if (commandLine.hasOption("f")) {
            format = commandLine.getOptionValue(formatOption);
        }
        if (commandLine.hasOption("h")) {
            HTMLTemplatePath = commandLine.getOptionValue(HTMLTemplatePathOption);
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
            GitCommitsParser parser = new GitCommitsParser(gitLogFile, regex, formatEnum, HTMLTemplatePath);

            System.out.println(parser.parse());
        } catch (IOException e) {
            logger.error("The file on the " + gitLogFile + " path was not found", e);

            System.exit(2);
        }
    }
}