package parser.gitCommitsParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.gitCommitsParser.converter.ConverterFactory;
import parser.gitCommitsParser.converter.Format;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitCommitsParser {
    private static final Logger logger = LoggerFactory.getLogger(GitCommitsParser.class);

    private final String gitLogFile;
    private final String regex;
    private final Format format;

    public GitCommitsParser(String gitLogFile, String regex, Format format) {
        this.gitLogFile = gitLogFile;
        this.regex = regex;
        this.format = format;
    }

    public void parse() throws IOException {
        List<String> listOfCommits = getStringOfCommitsFromFile();

        List<Map<String, String>> splitCommits = splitCommitsIntoParts(listOfCommits);

        ConverterFactory converter = new ConverterFactory();

        String result = converter.convert(splitCommits, format);

        logger.info("The converting was completed successfully");

        System.out.println(result);
    }

    private List<Map<String, String>> splitCommitsIntoParts(List<String> listOfCommits) {
        Map<String, String> mapOfGroupNameAndRegex = new HashMap<>();

        Pattern patternOfGroupName = Pattern.compile("\\(\\?<([a-zA-Z][a-zA-Z\\d]*)>");

        Matcher matcherOfGroupName = patternOfGroupName.matcher(regex);

        List<Integer> listOfStartAndEndIndexes = new ArrayList<>();

        matcherOfGroupName.results().forEach(matchResult -> {
            listOfStartAndEndIndexes.add(matchResult.start());
            listOfStartAndEndIndexes.add(matchResult.end());
        });

        for (int i = 0; i < listOfStartAndEndIndexes.size() - 2; i += 2) {
            mapOfGroupNameAndRegex.put(
                    regex.substring(listOfStartAndEndIndexes.get(i) + 3, listOfStartAndEndIndexes.get(i + 1) - 1),
                    regex.substring(listOfStartAndEndIndexes.get(i + 1), listOfStartAndEndIndexes.get(i + 2) - 1)
            );
        }
        mapOfGroupNameAndRegex.put(
                regex.substring(listOfStartAndEndIndexes.get(listOfStartAndEndIndexes.size() - 2) + 3, listOfStartAndEndIndexes.get(listOfStartAndEndIndexes.size() - 1) - 1),
                regex.substring(listOfStartAndEndIndexes.get(listOfStartAndEndIndexes.size() - 1), regex.length() - 1)
        );

        List<Map<String, String>> listOfCommitMap = new ArrayList<>();

        for (String commit : listOfCommits) {
            Map<String, String> commitMap = new HashMap<>();

            for (String element : mapOfGroupNameAndRegex.keySet()) {
                Matcher matcherOfElement = Pattern.compile(mapOfGroupNameAndRegex.get(element)).matcher(commit);

                if (matcherOfElement.find()) {
                    commitMap.put(element, matcherOfElement.group());
                }
            }

            listOfCommitMap.add(commitMap);
        }

        return listOfCommitMap;
    }

    private List<String> getStringOfCommitsFromFile() throws IOException {
        return Files.readAllLines(Paths.get(gitLogFile));
    }
}