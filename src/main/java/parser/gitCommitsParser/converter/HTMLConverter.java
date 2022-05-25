package parser.gitCommitsParser.converter;

import java.util.List;
import java.util.Map;

public class HTMLConverter implements Converter {
    @Override
    public String convert(List<Map<String, String>> splitCommits) {
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
}
