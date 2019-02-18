package ru.job4j.sockets.filemanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Реализация серверного обработчика объектов запроса.
 */
public class ServerRequestHandler implements RequestHandler {
    private final static Logger LOGGER = LogManager.getLogger(ServerRequestHandler.class);
    private Map<Integer, Function<TransferObject, TransferObject>> handlerMap = new HashMap<>();

    @Override
    public TransferObject handleRequest(TransferObject requestObject) {
        return handlerMap.
                getOrDefault(
                        requestObject.getCommand(),
                        transferObject ->
                                new TransferObject(
                                        Commands.ERROR.ordinal(),
                                        String.format(
                                                "Command not supported. CommandKey: %s",
                                                transferObject.getCommand())))
                .apply(requestObject);
    }

    public void initCommands() {
        this.handlerMap.put(
                Commands.ERROR.ordinal(),
                new ErrorCommandHandler());
        this.handlerMap.put(
                Commands.MSG.ordinal(),
                new MsgCommandHandler());
        this.handlerMap.put(
                Commands.HELP.ordinal(),
                transferObject -> new TransferObject(Commands.MSG.ordinal(),this.supportedCommands()));
    }

    public String supportedCommands() {
        return handlerMap.entrySet()
                .stream()
                .map((e) -> Commands.values()[e.getKey()].getHelpDescription())
                .collect(Collectors.joining(Strings.LINE_SEPARATOR));
    }

}
