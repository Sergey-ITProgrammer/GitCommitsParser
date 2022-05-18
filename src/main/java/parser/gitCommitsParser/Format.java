package parser.gitCommitsParser;

import org.json.simple.JSONObject;

import java.util.List;

public class Format {
    public static String toPlain(List<String[]> splitCommits) {

        StringBuilder result = new StringBuilder();

        for (String[] listOfParts : splitCommits) {
            if (listOfParts[Component.commitIdIndex] != null) {
                result.append(Component.commitId + ": ").append(listOfParts[Component.commitIdIndex]).append("\n");
            }
            if (listOfParts[Component.typeIndex] != null) {
                result.append(Component.type + ": ").append(listOfParts[Component.typeIndex]).append("\n");
            }
            if (listOfParts[Component.jiraIssueIndex] != null) {
                result.append(Component.jiraIssue + ": ").append(listOfParts[Component.jiraIssueIndex]).append("\n");
            }
            if (listOfParts[Component.programComponentIndex] != null) {
                result.append(Component.programComponent + ": ").append(listOfParts[Component.programComponentIndex]).append("\n");
            }
            if (listOfParts[Component.textIndex] != null) {
                result.append(Component.text + ": ").append(listOfParts[Component.textIndex]).append("\n");
            }

            result.append("\n");
        }

        return result.toString();
    }

    public static String toHTML(List<String[]> splitCommits) {
        StringBuilder result = new StringBuilder();

        result.append("<body>" + "\n");

        for (String[] listOfParts : splitCommits) {
            if (listOfParts[Component.commitIdIndex] != null) {
                result.append("\t<h1>" + Component.commitId + ": ")
                        .append(listOfParts[Component.commitIdIndex]).append("</h1>").append("\n");
            }
            if (listOfParts[Component.typeIndex] != null) {
                result.append("\t<h2>" + Component.type + ": ")
                        .append(listOfParts[Component.typeIndex]).append("</h2>").append("\n");
            }
            if (listOfParts[Component.jiraIssueIndex] != null) {
                result.append("\t<h2>" + Component.jiraIssue + ": ")
                        .append(listOfParts[Component.jiraIssueIndex]).append("</h2>").append("\n");
            }
            if (listOfParts[Component.programComponentIndex] != null) {
                result.append("\t<h2>" + Component.programComponent + ": ")
                        .append(listOfParts[Component.programComponentIndex]).append("</h2>").append("\n");
            }
            if (listOfParts[Component.textIndex] != null) {
                result.append("\t<h2>" + Component.text +  ": ").append(listOfParts[Component.textIndex]).append("</h2>").append("\n");
            }

            result.append("\n");
        }

        result.append("</body>");

        return result.toString();
    }

    public static String toJSON(List<String[]> splitCommits) {
        JSONObject jsonResult = new JSONObject();

        for (int i = 0; i < splitCommits.size(); i++) {
            JSONObject jsonObject = new JSONObject();

            if (splitCommits.get(i)[Component.commitIdIndex] != null) {
                jsonObject.put(Component.commitId, splitCommits.get(i)[Component.commitIdIndex]);
            }
            if (splitCommits.get(i)[Component.typeIndex] != null) {
                jsonObject.put(Component.type, splitCommits.get(i)[Component.typeIndex]);
            }
            if (splitCommits.get(i)[Component.jiraIssueIndex] != null) {
                jsonObject.put(Component.jiraIssue, splitCommits.get(i)[Component.jiraIssueIndex]);
            }
            if (splitCommits.get(i)[Component.programComponentIndex] != null) {
                jsonObject.put(Component.programComponent, splitCommits.get(i)[Component.programComponentIndex]);
            }
            if (splitCommits.get(i)[Component.textIndex] != null) {
                jsonObject.put(Component.text, splitCommits.get(i)[Component.textIndex]);
            }

            jsonResult.put(i + 1, jsonObject);
        }

        return jsonResult.toJSONString();
    }
}
