package parser.gitCommitsParser.converter;

import java.util.List;
import java.util.Map;

public class PlainConverter implements Converter {
    @Override
    public String convert(List<Map<String, String>> splitCommits) {
        StringBuilder result = new StringBuilder();

        for (Map<String, String> listOfParts : splitCommits) {
            for (String component : listOfParts.keySet()) {
                result.append(component).append(": ").append(listOfParts.get(component)).append("\n");
            }

            result.append("\n");
        }

        return result.toString();
    }
}
