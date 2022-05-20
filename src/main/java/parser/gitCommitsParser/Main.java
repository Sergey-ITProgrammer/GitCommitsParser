package parser.gitCommitsParser;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main (String[] args) {

        String gitLogFile = "";
        String regex = "(?<text>.+)";
        String format = "";

        Option gitLogFileOption = new Option("l", "gitLogFile", true, "git log file");
        Option patternOption = new Option("r", "regex", true, "regex");
        Option formatOption = new Option("f", "format", true, "format");

//        gitLogFileOption.setRequired(true);

        Options options = new Options();

        options.addOption(gitLogFileOption);
        options.addOption(patternOption);
        options.addOption(formatOption);

        CommandLineParser parser = new DefaultParser();

        CommandLine commandLine = null;
        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException e) {
            logger.error("", e);

            System.exit(1);
        }

        if (!commandLine.hasOption("l") && !commandLine.hasOption("r") && !commandLine.hasOption("f")) {
            System.out.println("-l --gitLogFile: path to git log file\n" +
                    "-r --regex: regex to parse commits\n" +
                    "-f --format: format to parse commits");
            System.exit(1);
        }

        if (commandLine.hasOption("l")) {
            gitLogFile = commandLine.getOptionValue(gitLogFileOption);
        }
        if (commandLine.hasOption("r")) {
            regex = commandLine.getOptionValue(patternOption);
        }
        if (commandLine.hasOption("f")) {
            format = commandLine.getOptionValue(formatOption);
        }

        new GitCommitsParser("/home/sergey/Desktop/gitLogTest", "^(?<commitId>\\w+)\\s(?<type>\\w+)(?<jiraIssue>\\(.+\\))[:\\.]\\s(?<text>.+)$", "json").parse();
    }
}
