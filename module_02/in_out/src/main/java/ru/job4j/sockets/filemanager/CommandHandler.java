package ru.job4j.sockets.filemanager;

import java.util.function.Function;

/**
 * Функциональный интерфейс обработчика команд объектов запросов.
 */
@FunctionalInterface
public interface CommandHandler extends Function<TransferObject, TransferObject> {
}
