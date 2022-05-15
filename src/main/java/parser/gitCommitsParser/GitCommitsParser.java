package parser.gitCommitsParser;

import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitCommitsParser {
    private final String gitLogFile;
    private final String directoryToCreateFile;
    private final String pattern;
    private final String format;

    public GitCommitsParser(String gitLogFile, String directoryToCreateFile, String pattern, String format) {
        this.gitLogFile = gitLogFile;
        this.directoryToCreateFile = directoryToCreateFile;
        this.pattern = pattern;
        this.format = format;
    }

    public void parse() {
        List<String> listOfCommits = getListOfCommitsFromFile(gitLogFile);

        List<String[]> splitCommits = splitCommitsIntoParts(listOfCommits);

        if (format.equals("json")) {
            createJSONFile(splitCommits);
        }
        if (format.equals("html")) {
            createHTMLFile(splitCommits);
        }
        if (format.equals("plain")) {
            createPlainFile(splitCommits);
        }
    }

    private void createPlainFile(List<String[]> splitCommits) {
        Path newFilePath = Path.of(directoryToCreateFile + "/parsedGitCommits.txt");

        try {
            Files.createFile(newFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileWriter fileWriter = new FileWriter(newFilePath.toString(), true)) {
            for (String[] listOfParts : splitCommits) {
                if (pattern.contains("$commitId")) {
                    fileWriter.write("CommitId: " + listOfParts[0] + "\n");
                }
                if (pattern.contains("$type")) {
                    fileWriter.write("Type: " + listOfParts[1] + "\n");
                }
                if (pattern.contains("$jiraIssue")) {
                    fileWriter.write("JiraIssue: " + listOfParts[2] + "\n");
                }

                if (!pattern.contains("$programComponent") && pattern.contains("$text")) {
                    fileWriter.write("Text: " + listOfParts[3] + " - " + listOfParts[4] + "\n");
                } else {
                    if (pattern.contains("$programComponent")) {
                        fileWriter.write("ProgramComponent: " + listOfParts[3] + "\n");
                    }
                    if (pattern.contains("$text")) {
                        fileWriter.write("Text: " + listOfParts[4] + "\n");
                    }
                }

                fileWriter.write("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (Files.exists(newFilePath)) {
            System.out.println("The plain file was created successfully\n" +
                    "File path: " + directoryToCreateFile + "/parsedGitCommits.txt");
        }
    }

    private void createHTMLFile(List<String[]> splitCommits) {
        Path newFilePath = Path.of(directoryToCreateFile + "/parsedGitCommits.html");

        try {
            Files.createFile(newFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileWriter fileWriter = new FileWriter(newFilePath.toString(), true)) {
            fileWriter.write("<body>" + "\n");

            for (String[] listOfParts : splitCommits) {
                if (pattern.contains("$commitId")) {
                    fileWriter.write("\t<h1>" + "CommitId: " + listOfParts[0] + "</h1>" + "\n");
                }
                if (pattern.contains("$type")) {
                    fileWriter.write( "\t<h2>" + "Type: " + listOfParts[1] + "</h2>" + "\n");
                }
                if (pattern.contains("$jiraIssue")) {
                    fileWriter.write("\t<h2>" + "JiraIssue: " + listOfParts[2] + "</h2>" + "\n");
                }

                if (!pattern.contains("$programComponent") && pattern.contains("$text")) {
                    fileWriter.write( "\t<h2>" + "Text: " + listOfParts[3] + " - " + listOfParts[4] + "</h2>" + "\n");
                } else {
                    if (pattern.contains("$programComponent")) {
                        fileWriter.write( "\t<h2>" + "ProgramComponent: " + listOfParts[3] + "</h2>" + "\n");
                    }
                    if (pattern.contains("$text")) {
                        fileWriter.write("\t<h2>" + "Text: " + listOfParts[4] + "</h2>" + "\n");
                    }
                }

                fileWriter.write("\n");
            }

            fileWriter.write("</body>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (Files.exists(newFilePath)) {
            System.out.println("The HTML file was created successfully\n" +
                    "File path: " + directoryToCreateFile + "/parsedGitCommits.html");
        }
    }

    private void createJSONFile(List<String[]> splitCommits) {
        JSONObject jsonResult = new JSONObject();

        for (int i = 0; i < splitCommits.size(); i++) {
            JSONObject jsonObject = new JSONObject();

            if (pattern.contains("$commitId")) {
                jsonObject.put("commitId", splitCommits.get(i)[0]);
            }
            if (pattern.contains("$type")) {
                jsonObject.put("type", splitCommits.get(i)[1]);
            }
            if (pattern.contains("$jiraIssue")) {
                jsonObject.put("jiraIssue", splitCommits.get(i)[2]);
            }

            if (!pattern.contains("$programComponent") && pattern.contains("$text")) {
                jsonObject.put("text", splitCommits.get(i)[3] + " - " + splitCommits.get(i)[4]);
            } else {
                if (pattern.contains("$programComponent")) {
                    jsonObject.put("programComponent", splitCommits.get(i)[3]);
                }
                if (pattern.contains("$text")) {
                    jsonObject.put("text", splitCommits.get(i)[4]);
                }
            }

            jsonResult.put(i + 1, jsonObject);
        }

        Path newFilePath = Path.of(directoryToCreateFile + "/parsedGitCommits.json");

        try {
            Files.createFile(newFilePath);

            Files.write(newFilePath, jsonResult.toJSONString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (Files.exists(newFilePath)) {
            System.out.println("The JSON file was created successfully\n" +
                    "File path: " + directoryToCreateFile + "/parsedGitCommits.json");
        }
    }

    // listOfSplitCommits is a list of string arrays where:
    //
    // String[0] = $commitId
    // String[1] = $type
    // String[2] = $jiraIssue
    // String[3] = $programComponent
    // String[4] = $text
    private List<String[]> splitCommitsIntoParts(List<String> listOfCommits) {
        List<String[]> listOfSplitCommits = new ArrayList<>();

        Pattern patternOfCommitId = Pattern.compile("^\\w+\\s");
        Pattern patternOfTypeAndJiraIssue = Pattern.compile("\\s\\w+\\(.+\\)[\\.:]");
        Pattern patternOfText = Pattern.compile("[\\.:]\\s.+");
        Pattern patternOfProgramComponentAndText = Pattern.compile("[\\.:]\\s.+\\-\\s.+");

        for (String commit : listOfCommits) {
            String[] listOfCommitParts = new String[5];

            if (commit.matches("^\\w{7}\\s\\w+\\(.+\\)[\\.:]\\s.+\\-\\s.+")) {
                Matcher matcherOfCommitId = patternOfCommitId.matcher(commit);
                if (matcherOfCommitId.find()) {
                    listOfCommitParts[0] = matcherOfCommitId.group().trim();
                }

                Matcher matcherOfTypeAndJiraIssue = patternOfTypeAndJiraIssue.matcher(commit);
                if (matcherOfTypeAndJiraIssue.find()) {
                    Matcher matcherOfJiraIssue = Pattern.compile("\\(.+\\)").matcher(matcherOfTypeAndJiraIssue.group());

                    if (matcherOfJiraIssue.find()) {
                        listOfCommitParts[1] = matcherOfTypeAndJiraIssue.group().substring(0, matcherOfJiraIssue.start()).trim();

                        listOfCommitParts[2] = matcherOfJiraIssue.group().trim();
                    }
                }

                Matcher matcherOfProgramComponentAndText = patternOfProgramComponentAndText.matcher(commit);
                if (matcherOfProgramComponentAndText.find()) {
                    String[] arr = matcherOfProgramComponentAndText.group().split("-", 2);

                    listOfCommitParts[3] = arr[0].substring(1).trim();
                    listOfCommitParts[4] = arr[1].trim();
                }
            }
            else if (commit.matches("^\\w{7}\\s\\w+\\(.+\\)[\\.:]\\s.+")) {
                Matcher matcherOfCommitId = patternOfCommitId.matcher(commit);
                if (matcherOfCommitId.find()) {
                    listOfCommitParts[0] = matcherOfCommitId.group().trim();
                }

                Matcher matcherOfTypeAndJiraIssue = patternOfTypeAndJiraIssue.matcher(commit);
                if (matcherOfTypeAndJiraIssue.find()) {
                    Matcher matcherOfJiraIssue = Pattern.compile("\\(.+\\)").matcher(matcherOfTypeAndJiraIssue.group());

                    if (matcherOfJiraIssue.find()) {
                        listOfCommitParts[1] = matcherOfTypeAndJiraIssue.group().substring(0, matcherOfJiraIssue.start()).trim();

                        listOfCommitParts[2] = matcherOfJiraIssue.group().trim();
                    }
                }

                Matcher matcherOfText = patternOfText.matcher(commit);
                if (matcherOfText.find()) {
                    listOfCommitParts[4] = matcherOfText.group().substring(1).trim();
                }
            }
            else if (commit.matches("^\\w{7}\\s.+")) {
                Matcher matcherOfCommitId = patternOfCommitId.matcher(commit);
                if (matcherOfCommitId.find()) {
                    listOfCommitParts[0] = matcherOfCommitId.group().trim();
                }

                Matcher matcherOfText = Pattern.compile("\\s.+").matcher(commit);
                if (matcherOfText.find()) {
                    listOfCommitParts[4] = matcherOfText.group().trim();
                }
            }
            else {
                listOfCommitParts[4] = commit;
            }

            listOfSplitCommits.add(listOfCommitParts);
        }

        return listOfSplitCommits;
    }

    private List<String> getListOfCommitsFromFile(String pathToCommitFile) {
        List<String> listOfCommits;
        try {
            listOfCommits = Files.readAllLines(Paths.get(pathToCommitFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return listOfCommits;
    }
}