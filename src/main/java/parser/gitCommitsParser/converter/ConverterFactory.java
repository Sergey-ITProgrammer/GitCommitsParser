package parser.gitCommitsParser.converter;

import java.util.List;
import java.util.Map;

public class ConverterFactory {
    private final String HTMLTemplatePath;

    public ConverterFactory(String HTMLTemplatePath) {
        this.HTMLTemplatePath = HTMLTemplatePath;
    }

    public String convert(List<Map<String, String>> splitCommits, Format format) {
        String result = "";

        switch (format) {
            case JSON:
                result = new JSONConverter().convert(splitCommits);
                break;
            case HTML:
                result = new HTMLConverter(HTMLTemplatePath).convert(splitCommits);
                break;
            case PLAIN:
                result = new PlainConverter().convert(splitCommits);
                break;
        }

        return result;
    }
}
