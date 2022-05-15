# GitCommitsParser

## What is it?
Java-приложение для парсинга git-лога.

## How it works?
Приложение упаковано в jar-файл, находящийся в директории target.

#### Параметры запуска приложения:
1) -gitLogFile= 
(путь до файла с git-логом)
2) -directoryToCreateFile= 
(директория, где будет создан файл. Имя файла - parsedGitCommits.json/html/txt)
3) -pattern=
($commitId, $type, $jiraIssue, $programComponent, $text)
4) -format=
(json, html или plain)

#### Пример запуска приложения:
java -jar GitCommitsParser-1.0.jar -gitLogFile=/home/user/Desktop/gitLog -format=json -pattern='$commitId $text' -directoryToCreateFile=/home/user/Desktop