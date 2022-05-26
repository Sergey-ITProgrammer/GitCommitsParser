package parser.gitCommitsParser.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.exceptions.TemplateEngineException;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.util.List;
import java.util.Map;

public class HTMLConverter implements Converter {
    private static final Logger logger = LoggerFactory.getLogger(HTMLConverter.class);
    private final String HTMLTemplatePath;

    public HTMLConverter(String HTMLTemplatePath) {
        this.HTMLTemplatePath = HTMLTemplatePath;
    }
    @Override
    public String convert(List<Map<String, String>> splitCommits) {
        Context context = new Context();
        context.setVariable("commits", splitCommits);

        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        try {
            if (!HTMLTemplatePath.isEmpty()) {
                return templateEngine.process(HTMLTemplatePath, context);
            }
            return templateEngine.process("classes/defaultHTMLTemplate.html", context);
        } catch (TemplateEngineException e) {
            logger.error("Exception processing template", e);

            System.exit(2);
        }

        return "";
    }
}
