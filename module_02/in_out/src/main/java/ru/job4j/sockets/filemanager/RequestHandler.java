package ru.job4j.sockets.filemanager;

/**
 * Интерфейс обработчика объектов запроса.
 */
public interface RequestHandler {

    TransferObject handleRequest(TransferObject requestObject);

}
