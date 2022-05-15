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

#### Строки, которые программа обратывает корректно (в противном случае вся строка будет присвоена полю $text):
1) ^\w{7}\s\w+\(.+\)[\.:]\s.+\-\s.+ (EX: fr154c5 fix(WT-999). Компонент - описание коммита; fr154c5 fix(WT-999): Компонент - описание коммита)
2) ^\w{7}\s\w+\(.+\)[\.:]\s.+ (EX: fr154c5 fix(WT-999). Описание коммита; fr154c5 fix(WT-999): Описание коммита)
3) ^\w{7}\s.+ (EX: fr154c5 Описание коммита)

#### Пример запуска приложения:
java -jar GitCommitsParser-1.0.jar -gitLogFile=/home/user/Desktop/gitLog -format=json -pattern='$commitId $text' -directoryToCreateFile=/home/user/Desktop