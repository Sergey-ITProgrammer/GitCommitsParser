package parser.gitCommitsParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitCommitsParser {
    private static final Logger logger = LoggerFactory.getLogger(GitCommitsParser.class);

    private final String gitLogFile;
    private final String regex;
    private final String format;

    public GitCommitsParser(String gitLogFile, String regex, String format) {
        this.gitLogFile = gitLogFile;
        this.regex = regex;
        this.format = format;
    }

    public void parse() {
        List<String> listOfCommits = getListOfCommitsFromFile(gitLogFile);

        List<String[]> splitCommits = splitCommitsIntoParts(listOfCommits);

        String result = "";

        if (format.equals("json")) {
            logger.info("Converting to JSON");

            result = Format.toJSON(splitCommits);
        }
        if (format.equals("html")) {
            logger.info("Converting to HTML");

            result = Format.toHTML(splitCommits);
        }
        if (format.equals("plain")) {
            logger.info("Converting to plain");

            result = Format.toPlain(splitCommits);
        }

        logger.info("The converting was completed successfully");

        System.out.println(result);
    }

    private List<String[]> splitCommitsIntoParts(List<String> listOfCommits) {
        List<String[]> listOfSplitCommits = new ArrayList<>();

        Pattern pattern = Pattern.compile(regex);

        logger.info("Processing list of commits");

        for (String commit : listOfCommits) {
            String[] listOfCommitParts = new String[5];

            Matcher matcher = pattern.matcher(commit);

            if (matcher.find()) {
                if (regex.contains(String.format("<%s>", Component.commitId))) {
                    listOfCommitParts[Component.commitIdIndex] = matcher.group(Component.commitId);
                }
                if (regex.contains(String.format("<%s>", Component.type))) {
                    listOfCommitParts[Component.typeIndex] = matcher.group(Component.type);
                }
                if (regex.contains(String.format("<%s>", Component.jiraIssue))) {
                    listOfCommitParts[Component.jiraIssueIndex] = matcher.group(Component.jiraIssue);
                }
                if (regex.contains(String.format("<%s>", Component.programComponent))) {
                    listOfCommitParts[Component.programComponentIndex] = matcher.group(Component.programComponent);
                }
                if (regex.contains(String.format("<%s>", Component.text))) {
                    listOfCommitParts[Component.textIndex] = matcher.group(Component.text);
                }

                listOfSplitCommits.add(listOfCommitParts);
            }
        }

        return listOfSplitCommits;
    }

    private List<String> getListOfCommitsFromFile(String pathToCommitFile) {
        List<String> listOfCommits = new ArrayList<>();

        try {
            logger.info("Scanning file on the " + pathToCommitFile + " path");

            listOfCommits = Files.readAllLines(Paths.get(pathToCommitFile));
        } catch (IOException e) {
            logger.warn("The file on the " + pathToCommitFile + " path was not found", e);
        }

        return listOfCommits;
    }
}