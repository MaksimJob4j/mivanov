package ru.job4j.sockets.filemanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Реализация серверного обработчика объектов запроса.
 */
public class ClientRequestHandler implements RequestHandler {
    private final static Logger LOGGER = LogManager.getLogger(ClientRequestHandler.class);
    private Map<Integer, Function<TransferObject, TransferObject>> handlerMap = new HashMap<>();

    @Override
    public TransferObject handleRequest(TransferObject requestObject) {
        LOGGER.traceEntry();
        return handlerMap.
                getOrDefault(
                        requestObject.getCommand(),
                        transferObject ->
                                new TransferObject(
                                        Commands.ERROR.ordinal(),
                                        String.format(
                                                "Command not supported. CommandKey: %s",
                                                Commands.values()[transferObject.getCommand()])))
                .apply(requestObject);
    }

    @Override
    public void initCommands() {
        LOGGER.traceEntry();
        this.handlerMap.put(
                Commands.ERROR.ordinal(),
                new ErrorCommandHandler());
        this.handlerMap.put(
                Commands.MSG.ordinal(),
                new MsgCommandHandler());
        this.handlerMap.put(
                Commands.HELP.ordinal(),
                transferObject -> new TransferObject(Commands.MSG.ordinal(), this.supportedCommands()));
        this.handlerMap.put(
                Commands.LOAD.ordinal(),
                transferObject -> {
                    try {
                        boolean tryGetFolder = true;
                        String stringPath = "";
                        Scanner scanner = new Scanner(System.in);
                        while (tryGetFolder) {
                            System.out.println("Введите путь к папке сохранения.");
                            stringPath = scanner.nextLine();
                            File folder = new File(stringPath);
                            if (folder.exists() && folder.isDirectory()) {
                                tryGetFolder = false;
                            } else {
                                System.out.println(String.format("Папки %s не существует", stringPath));
                                System.out.println("Хотите прервать загрузку файла? (y)");
                                if ("y".equals(scanner.nextLine().trim().toLowerCase())) {
                                    stringPath = "";
                                    tryGetFolder = false;
                                }
                            }
                        }

                        if (stringPath.isEmpty()) {
                            System.out.println("Загрузка файла отменена :(");
                        } else {
                            File file = new File(
                                    stringPath
                                            + File.separator
                                            + transferObject.getParameter());
                            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                                fileOutputStream.write(transferObject.getData());
                                System.out.println(String.format("Файл %s загружен :)", transferObject.getParameter()));
                            }
                        }

                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                        System.out.println("Загрузка файла отменена :(");
                    }
                    return null;
                });
    }

    @Override
    public String supportedCommands() {
        LOGGER.traceEntry();
        return handlerMap.entrySet()
                .stream()
                .map((e) -> Commands.values()[e.getKey()].getHelpDescription())
                .collect(Collectors.joining(Strings.LINE_SEPARATOR));
    }
}
