import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.gitCommitsParser.GitCommitsParser;
import parser.gitCommitsParser.converter.Format;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JUnitTest extends Assertions {
    private static final Logger logger = LoggerFactory.getLogger(JUnitTest.class);
    private static List<String> listOfCommits;
    private final String regex = "(?<commitId>^\\w+)(?<jiraIssue>\\w+\\(.+\\))(?<text>[\\.:]\\s.+)";
    private static String requiredJsonResult;
    private static String requiredPlainResult;
    private static String requiredHTMLResult;

    @Test
    void testJsonFormat() {
        GitCommitsParser parser = new GitCommitsParser("", regex, Format.json, "");

        String result = parser.getParsedGitLog(listOfCommits);

        assertEquals(result, requiredJsonResult);

        logger.info("Json format test passed");
    }

    @Test
    void testPlainFormat() {
        GitCommitsParser parser = new GitCommitsParser("", regex, Format.plain, "");

        String result = parser.getParsedGitLog(listOfCommits);

        assertEquals(result, requiredPlainResult);

        logger.info("PLain format test passed");
    }

    @BeforeAll
    static void init() {
        listOfCommits = Arrays.stream((
                "e5dbf78 fix(WT-363). номенклатура - блюдо с модификаторами - не отображаются все модификаторы после добавления блюда в заказ\n" +
                "9e8f801 fix(WT-230): WT - Активация - Добавить обработку ошибки при активации терминала не хватает лицензий\n" +
                "057ad47 chore(Sentry): поставил заглушку на Sentry\n" +
                "4815ec5 fix(WT-361): Принтеры для кухни - автоматически закрывать заказ, если он был отправлен на кухонный принтер, и оплачен\n" +
                "ecff960 fix(WT-233): WТ - Тик. Принтер - если в запросе prepare, status = \"SENT_TO_KITCHEN\" после оплаты не выводить окно - \"Не все блюда были приготовлены...\"\n" +
                "a5504cd fix(WT-319): Устройства - оборудование - добавить иконку для принтеров чеков\n" +
                "69f039f fix(WT-318): ВФР - при добавлении Атола - название принтера \"кухонный дисплей\"\n" +
                "30f0d9d fix(WT-348): WT - Поиск по блюдам внутри папок не находит блюда\n" +
                "709aec1 fix(WT-347): Комбо - При заказе на главном меню блюдо, не продающееся частями (WHOLE), складывается с таким же подарочным\n" +
                "bac3b99 fix(WT-333): Комбо - блюдо с возможностью продаваться частями - позволяет сработать акции комбо, а затем удалить одно из блюд по акции.\n" +
                "e4bdd46 fix(WT-342): Комбо - если акционное блюдо = подарочному блюду при выборе подарка - подарочное блюдо складывается с акционным\n" +
                "03c6050 feat(WT-250, WT-267). Рефакторинг меню - разбил на компоненты, и оказалось что \"обычное\" и \"акционное\" меню могут существовать в рамках одного экрана.\n" +
                "fe37ec5 fix(WT-341): Комбо - изменение в меньшую сторону кол-ва подарочных блюд применённых в акции\n" +
                "42b8275 fix(WT-333): Комбо - блюдо с возможностью продаваться частями - позволяет сработать акции комбо, а затем удалить одно из блюд по акции.\n" +
                "facb7da fix(WT-333): Комбо - блюдо с возможностью продаваться частями - позволяет сработать акции комбо, а затем удалить одно из блюд по акции.\n" +
                "b42bb9d feat(WT-199, WT-200). Покрыл тестами `BonusesWriteOffCalculator` и начал писать тестовые утилиты - стаб-стор, генерация стаб-заказа и.т.д."
        ).split("\n")).collect(Collectors.toList());

        requiredJsonResult =
                "{\"1\":{\"jiraIssue\":\"fix(WT-363)\",\"commitId\":\"e5dbf78\",\"text\":\". номенклатура - блюдо с модификаторами - не отображаются все модификаторы после добавления блюда в заказ\"}," +
                "\"2\":{\"jiraIssue\":\"fix(WT-230)\",\"commitId\":\"9e8f801\",\"text\":\": WT - Активация - Добавить обработку ошибки при активации терминала не хватает лицензий\"}," +
                "\"3\":{\"jiraIssue\":\"chore(Sentry)\",\"commitId\":\"057ad47\",\"text\":\": поставил заглушку на Sentry\"}," +
                "\"4\":{\"jiraIssue\":\"fix(WT-361)\",\"commitId\":\"4815ec5\",\"text\":\": Принтеры для кухни - автоматически закрывать заказ, если он был отправлен на кухонный принтер, и оплачен\"}," +
                "\"5\":{\"jiraIssue\":\"fix(WT-233)\",\"commitId\":\"ecff960\",\"text\":\": WТ - Тик. Принтер - если в запросе prepare, status = \\\"SENT_TO_KITCHEN\\\" после оплаты не выводить окно - \\\"Не все блюда были приготовлены...\\\"\"}," +
                "\"6\":{\"jiraIssue\":\"fix(WT-319)\",\"commitId\":\"a5504cd\",\"text\":\": Устройства - оборудование - добавить иконку для принтеров чеков\"}," +
                "\"7\":{\"jiraIssue\":\"fix(WT-318)\",\"commitId\":\"69f039f\",\"text\":\": ВФР - при добавлении Атола - название принтера \\\"кухонный дисплей\\\"\"}," +
                "\"8\":{\"jiraIssue\":\"fix(WT-348)\",\"commitId\":\"30f0d9d\",\"text\":\": WT - Поиск по блюдам внутри папок не находит блюда\"}," +
                "\"9\":{\"jiraIssue\":\"fix(WT-347): Комбо - При заказе на главном меню блюдо, не продающееся частями (WHOLE)\",\"commitId\":\"709aec1\",\"text\":\": Комбо - При заказе на главном меню блюдо, не продающееся частями (WHOLE), складывается с таким же подарочным\"}," +
                "\"10\":{\"jiraIssue\":\"fix(WT-333)\",\"commitId\":\"bac3b99\",\"text\":\": Комбо - блюдо с возможностью продаваться частями - позволяет сработать акции комбо, а затем удалить одно из блюд по акции.\"}," +
                "\"11\":{\"jiraIssue\":\"fix(WT-342)\",\"commitId\":\"e4bdd46\",\"text\":\": Комбо - если акционное блюдо = подарочному блюду при выборе подарка - подарочное блюдо складывается с акционным\"}," +
                "\"12\":{\"jiraIssue\":\"feat(WT-250, WT-267)\",\"commitId\":\"03c6050\",\"text\":\". Рефакторинг меню - разбил на компоненты, и оказалось что \\\"обычное\\\" и \\\"акционное\\\" меню могут существовать в рамках одного экрана.\"}," +
                "\"13\":{\"jiraIssue\":\"fix(WT-341)\",\"commitId\":\"fe37ec5\",\"text\":\": Комбо - изменение в меньшую сторону кол-ва подарочных блюд применённых в акции\"}," +
                "\"14\":{\"jiraIssue\":\"fix(WT-333)\",\"commitId\":\"42b8275\",\"text\":\": Комбо - блюдо с возможностью продаваться частями - позволяет сработать акции комбо, а затем удалить одно из блюд по акции.\"}," +
                "\"15\":{\"jiraIssue\":\"fix(WT-333)\",\"commitId\":\"facb7da\",\"text\":\": Комбо - блюдо с возможностью продаваться частями - позволяет сработать акции комбо, а затем удалить одно из блюд по акции.\"}," +
                "\"16\":{\"jiraIssue\":\"feat(WT-199, WT-200)\",\"commitId\":\"b42bb9d\",\"text\":\". Покрыл тестами `BonusesWriteOffCalculator` и начал писать тестовые утилиты - стаб-стор, генерация стаб-заказа и.т.д.\"}}";

        requiredPlainResult =
                "jiraIssue: fix(WT-363)\n" +
                "commitId: e5dbf78\n" +
                "text: . номенклатура - блюдо с модификаторами - не отображаются все модификаторы после добавления блюда в заказ\n" +
                "\n" +
                "jiraIssue: fix(WT-230)\n" +
                "commitId: 9e8f801\n" +
                "text: : WT - Активация - Добавить обработку ошибки при активации терминала не хватает лицензий\n" +
                "\n" +
                "jiraIssue: chore(Sentry)\n" +
                "commitId: 057ad47\n" +
                "text: : поставил заглушку на Sentry\n" +
                "\n" +
                "jiraIssue: fix(WT-361)\n" +
                "commitId: 4815ec5\n" +
                "text: : Принтеры для кухни - автоматически закрывать заказ, если он был отправлен на кухонный принтер, и оплачен\n" +
                "\n" +
                "jiraIssue: fix(WT-233)\n" +
                "commitId: ecff960\n" +
                "text: : WТ - Тик. Принтер - если в запросе prepare, status = \"SENT_TO_KITCHEN\" после оплаты не выводить окно - \"Не все блюда были приготовлены...\"\n" +
                "\n" +
                "jiraIssue: fix(WT-319)\n" +
                "commitId: a5504cd\n" +
                "text: : Устройства - оборудование - добавить иконку для принтеров чеков\n" +
                "\n" +
                "jiraIssue: fix(WT-318)\n" +
                "commitId: 69f039f\n" +
                "text: : ВФР - при добавлении Атола - название принтера \"кухонный дисплей\"\n" +
                "\n" +
                "jiraIssue: fix(WT-348)\n" +
                "commitId: 30f0d9d\n" +
                "text: : WT - Поиск по блюдам внутри папок не находит блюда\n" +
                "\n" +
                "jiraIssue: fix(WT-347): Комбо - При заказе на главном меню блюдо, не продающееся частями (WHOLE)\n" +
                "commitId: 709aec1\n" +
                "text: : Комбо - При заказе на главном меню блюдо, не продающееся частями (WHOLE), складывается с таким же подарочным\n" +
                "\n" +
                "jiraIssue: fix(WT-333)\n" +
                "commitId: bac3b99\n" +
                "text: : Комбо - блюдо с возможностью продаваться частями - позволяет сработать акции комбо, а затем удалить одно из блюд по акции.\n" +
                "\n" +
                "jiraIssue: fix(WT-342)\n" +
                "commitId: e4bdd46\n" +
                "text: : Комбо - если акционное блюдо = подарочному блюду при выборе подарка - подарочное блюдо складывается с акционным\n" +
                "\n" +
                "jiraIssue: feat(WT-250, WT-267)\n" +
                "commitId: 03c6050\n" +
                "text: . Рефакторинг меню - разбил на компоненты, и оказалось что \"обычное\" и \"акционное\" меню могут существовать в рамках одного экрана.\n" +
                "\n" +
                "jiraIssue: fix(WT-341)\n" +
                "commitId: fe37ec5\n" +
                "text: : Комбо - изменение в меньшую сторону кол-ва подарочных блюд применённых в акции\n" +
                "\n" +
                "jiraIssue: fix(WT-333)\n" +
                "commitId: 42b8275\n" +
                "text: : Комбо - блюдо с возможностью продаваться частями - позволяет сработать акции комбо, а затем удалить одно из блюд по акции.\n" +
                "\n" +
                "jiraIssue: fix(WT-333)\n" +
                "commitId: facb7da\n" +
                "text: : Комбо - блюдо с возможностью продаваться частями - позволяет сработать акции комбо, а затем удалить одно из блюд по акции.\n" +
                "\n" +
                "jiraIssue: feat(WT-199, WT-200)\n" +
                "commitId: b42bb9d\n" +
                "text: . Покрыл тестами `BonusesWriteOffCalculator` и начал писать тестовые утилиты - стаб-стор, генерация стаб-заказа и.т.д.\n\n";
    }
}
