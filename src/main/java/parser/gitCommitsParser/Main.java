package parser.gitCommitsParser;

public class Main {
    public static void main(String[] args) {
        String gitLogFile = "";
        String directoryToCreateFile = "";
        String pattern = "";
        String format = "";

        for (String s : args) {
            String[] splitStringsByEqualSign = s.split("=");

            if (splitStringsByEqualSign[0].equals("-gitLogFile")) {
                gitLogFile = splitStringsByEqualSign[1];
            }
            if (splitStringsByEqualSign[0].equals("-directoryToCreateFile")) {
                directoryToCreateFile = splitStringsByEqualSign[1];
            }
            if (splitStringsByEqualSign[0].equals("-pattern")) {
                pattern = splitStringsByEqualSign[1];
            }
            if (splitStringsByEqualSign[0].equals("-format")) {
                format = splitStringsByEqualSign[1];
            }
        }

        new GitCommitsParser(gitLogFile, directoryToCreateFile, pattern, format).parse();
    }
}
