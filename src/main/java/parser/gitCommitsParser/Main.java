package parser.gitCommitsParser;

import org.apache.commons.cli.*;

public class Main {
    public static void main (String[] args) {
        String gitLogFile = "";
        String regex = "(?<text>.+)";
        String format = "plain";

        Option gitLogFileOption = new Option("l", "gitLogFile", true, "git log file");
        Option patternOption = new Option("r", "regex", true, "regex");
        Option formatOption = new Option("f", "format", true, "format");

        gitLogFileOption.setRequired(true);

        Options options = new Options();

        options.addOption(gitLogFileOption);
        options.addOption(patternOption);
        options.addOption(formatOption);

        CommandLineParser parser = new DefaultParser();

        CommandLine commandLine;
        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException e) {
            throw new RuntimeException(e);
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

        new GitCommitsParser(gitLogFile, regex, format).parse();
    }
}
