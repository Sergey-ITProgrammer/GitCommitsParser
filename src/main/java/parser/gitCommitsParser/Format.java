package parser.gitCommitsParser;

import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

public class Format {
    public static String toPlain(List<Map<String, String>> splitCommits) {
        StringBuilder result = new StringBuilder();

        for (Map<String, String> listOfParts : splitCommits) {
            for (String component : listOfParts.keySet()) {
                result.append(component).append(": ").append(listOfParts.get(component)).append("\n");
            }

            result.append("\n");
        }

        return result.toString();
    }

    public static String toHTML(List<Map<String, String>> splitCommits) {
        StringBuilder result = new StringBuilder();

        result.append("<body>" + "\n");

        for (Map<String, String> listOfParts : splitCommits) {
            for (String component : listOfParts.keySet()) {
                result.append("\t<h2>").append(component).append(": ").append(listOfParts.get(component)).append("</h2>\n");
            }

            result.append("\n");
        }

        result.append("</body>");

        return result.toString();
    }

    public static String toJSON(List<Map<String, String>> splitCommits) {
        JSONObject jsonCommits = new JSONObject();

        for (int i = 0; i < splitCommits.size(); i++) {
            JSONObject commit = new JSONObject();

            commit.putAll(splitCommits.get(i));

            jsonCommits.put(i + 1, commit);
        }

        return jsonCommits.toJSONString();
    }
}
