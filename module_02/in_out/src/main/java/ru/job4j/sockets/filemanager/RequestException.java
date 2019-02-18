package ru.job4j.sockets.filemanager;

/**
 * Класс ошибки формирования запроса.
 */
public class RequestException extends Exception {

    public RequestException(String errorMessage) {
        super(errorMessage);
    }
}
