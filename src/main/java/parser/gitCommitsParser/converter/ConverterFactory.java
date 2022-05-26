package parser.gitCommitsParser.converter;

import java.util.List;
import java.util.Map;

public class ConverterFactory {
    public String convert(List<Map<String, String>> splitCommits, Format format) {
        String result = "";

        Converter formatConverter;
        switch (format) {
            case json:
                formatConverter = new JSONConverter();

                result = formatConverter.convert(splitCommits);
                break;
            case html:
                formatConverter = new HTMLConverter();

                result = formatConverter.convert(splitCommits);
                break;
            case plain:
                formatConverter = new PlainConverter();

                result = formatConverter.convert(splitCommits);
        }

        return result;
    }
}
