# GitCommitsParser

## What is it?
Java-приложение для парсинга git-лога.

## How it works?

#### Параметры запуска приложения:
1) --gitLogFile (-l) 
(путь до файла с git-логом)
2) --regex (-r)
(регулярное выражение с использованием групп: commitId, type, jiraIssue, programComponent, text. 
Если параметр regex не указан, всё будет передано в поле text)
3) --format (-f)
(json, html или plain. По умолчанию: plain)

#### Пример запуска приложения:
java -jar GitCommitsParser-1.0.jar --gitLogFile=/home/user/Desktop/gitLog -f json -r='(?<commitId>^\w+)\s(?<text>.+)'