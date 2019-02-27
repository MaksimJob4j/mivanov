package ru.job4j.sockets.filemanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Реализация обработчика объекта запроса c командой error
 */
public class ErrorCommandHandler implements CommandHandler {
    private final static Logger LOGGER = LogManager.getLogger(ErrorCommandHandler.class);

    @Override
    public TransferObject apply(TransferObject requestObject) {
        LOGGER.traceEntry();
        System.out.println(String.format("ERROR: %n%s", requestObject.getParameter()));
        return null;
    }
}
