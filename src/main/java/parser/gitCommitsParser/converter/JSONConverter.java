package parser.gitCommitsParser.converter;

import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

public class JSONConverter implements Converter {
    @Override
    public String convert(List<Map<String, String>> splitCommits) {
        JSONObject jsonCommits = new JSONObject();

        for (int i = 0; i < splitCommits.size(); i++) {
            JSONObject commit = new JSONObject();

            commit.putAll(splitCommits.get(i));

            jsonCommits.put(i + 1, commit);
        }

        return jsonCommits.toJSONString();
    }
}
