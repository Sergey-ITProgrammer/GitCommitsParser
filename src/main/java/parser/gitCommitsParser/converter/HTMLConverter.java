package parser.gitCommitsParser.converter;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.util.List;
import java.util.Map;

public class HTMLConverter implements Converter {
    @Override
    public String convert(List<Map<String, String>> splitCommits) {
        FileTemplateResolver templateResolver = new FileTemplateResolver();

        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/home/sergey/Desktop/JavaProjects/GitCommitsParser/src/main/resources/");

        TemplateEngine templateEngine = new TemplateEngine();

        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();

        context.setVariable("commits", splitCommits);

        return templateEngine.process("defaultHTMLTemplate.html", context);
    }
}
