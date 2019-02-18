package ru.job4j.sockets.filemanager;

import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Перечень доступных команд.
 */
public enum Commands {
    HELP("help", "список команд", "help"),
    ERROR("error", "сообщение об ошибке", "error massageString"),
    MSG("msg", "отправить сообщение", "msg massageString"),
    DIR("dir", "список текущего каталога", "dir"),
    CHILD("child", "перейти в подкаталог", "child dirName"),
    PARENT("parent", "перейти в родительский каталог", "parent"),
    MAIN("main", "спуститься в родительский каталог", "main"),
    DOWNLOAD("download", "скачать файл", "download fileName"),
    LOAD("load", "загрузить файл", "load filePath"),
    EXIT("exit", "завершение сессии","msg");

    private String keyWord;
    private String description;
    private String syntax;

    private Commands(String keyWord, String description, String syntax) {
        this.keyWord = keyWord;
        this.description = description;
        this.syntax = syntax;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public String getDescription() {
        return description;
    }

    public String getSyntax() {
        return syntax;
    }

    public String getHelpDescription() {
        return String.format(
            "%s - %s (syntax: %s)", keyWord.toUpperCase(), description, syntax);
    }

    public static String getCommandsList() {
        return Arrays.stream(Commands.values())
                .map(Commands::getHelpDescription)
                .collect(Collectors.joining(Strings.LINE_SEPARATOR));
    }
}
