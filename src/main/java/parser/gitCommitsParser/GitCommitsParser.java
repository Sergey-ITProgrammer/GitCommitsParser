package parser.gitCommitsParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        List<String> listOfCommits = getStringOfCommitsFromFile(gitLogFile);

        List<Map<String, String>> splitCommits = splitCommitsIntoParts(listOfCommits);

        String result = "";

        switch (format) {
            case "json":
                result = Format.toJSON(splitCommits);
                break;
            case "html":
                result = Format.toHTML(splitCommits);
                break;
            default:
                result = Format.toPlain(splitCommits);
        }

        System.out.println(result);
    }

    private List<Map<String, String>> splitCommitsIntoParts(List<String> listOfCommits) {
        Pattern patternOfGroupName = Pattern.compile("<\\w+>");

        List<String> listOfGroupName = patternOfGroupName.matcher(regex)
                .results().map(MatchResult::group).map(s -> s.substring(1, s.length() - 1))
                .collect(Collectors.toList());

        Pattern patternOfCommit = Pattern.compile(regex);

        List<Map<String, String>> listOfCommitMap = new ArrayList<>();

        for (String commit : listOfCommits) {
            Matcher matcherOfCommit = patternOfCommit.matcher(commit);

            Map<String, String> commitMap = new HashMap<>();

            if (matcherOfCommit.find()) {
                for (int i = 1; i <= matcherOfCommit.groupCount(); i++) {
                    commitMap.put(listOfGroupName.get(i - 1), matcherOfCommit.group(i));
                }
            }

            listOfCommitMap.add(commitMap);
        }

        return listOfCommitMap;
    }

    private List<String> getStringOfCommitsFromFile(String pathToCommitFile) {
        List<String> listOfCommits = new ArrayList<>();

        try {
            listOfCommits = Files.readAllLines(Paths.get(gitLogFile));
        } catch (IOException e) {
            logger.error("The file on the " + pathToCommitFile + " path was not found", e);
        }

        return listOfCommits;
    }
}