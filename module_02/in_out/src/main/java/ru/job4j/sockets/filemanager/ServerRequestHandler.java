package ru.job4j.sockets.filemanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Реализация серверного обработчика объектов запроса.
 */
public class ServerRequestHandler implements RequestHandler {
    private final static Logger LOGGER = LogManager.getLogger(ServerRequestHandler.class);
    private Map<Integer, Function<TransferObject, TransferObject>> handlerMap = new HashMap<>();
    private final File root;
    private File current;;

    public ServerRequestHandler(File root) {
        this.root = root;
    }

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
        this.current = this.root;
        this.handlerMap.put(
                Commands.MSG.ordinal(),
                new MsgCommandHandler());
        this.handlerMap.put(
                Commands.HELP.ordinal(),
                transferObject -> new TransferObject(Commands.MSG.ordinal(), this.supportedCommands()));
        this.handlerMap.put(
                Commands.DIR.ordinal(),
                transferObject -> {
                    String fileList = Arrays.stream(this.current.listFiles())
                            .map(f -> f.getAbsolutePath().substring(this.current.getAbsolutePath().length() + 1))
                            .collect(Collectors.joining(Strings.LINE_SEPARATOR));
                    return new TransferObject(Commands.MSG.ordinal(), fileList);
                });
        this.handlerMap.put(
                Commands.CHILD.ordinal(),
                transferObject -> {
                    TransferObject response;
                    File newCurrent = new File(
                            this.current.getAbsolutePath()
                                    + File.separator
                                    + transferObject.getParameter());
                    if (newCurrent.exists()
                            && newCurrent.isDirectory()
                            && Arrays.stream(this.current.listFiles())
                            .map(f -> f.getAbsolutePath())
                            .anyMatch(s -> s.equals(newCurrent.getAbsolutePath()))) {
                        this.current = newCurrent;
                        response = new TransferObject(
                                Commands.MSG.ordinal(),
                                String.format("Текущая директория: %s",  transferObject.getParameter()));
                    } else {
                        response = new TransferObject(
                                Commands.ERROR.ordinal(),
                                String.format("Директории %s здесь не нет", transferObject.getParameter()));
                    }
                    return response;
                });
        this.handlerMap.put(
                Commands.PARENT.ordinal(),
                transferObject -> {
                    TransferObject response;
                    if (this.current.compareTo(this.root) != 0) {
                        this.current = this.current.getParentFile();
                        response = new TransferObject(
                                Commands.MSG.ordinal(),
                                String.format("Текущая директория: %s", this.current.getName()));
                    } else {
                        response = new TransferObject(
                                Commands.ERROR.ordinal(),
                                "Отказано в достепе - вы в корне");
                    }
                    return response;
                });
        this.handlerMap.put(
                Commands.MAIN.ordinal(),
                transferObject -> {
                    this.current = this.root;
                    return new TransferObject(
                            Commands.MSG.ordinal(),
                            String.format("Текущая директория: %s", this.current.getName()));
                });
        this.handlerMap.put(
                Commands.DOWNLOAD.ordinal(),
                transferObject -> {
                    TransferObject response;
                    File file = new File(
                            this.current.getAbsolutePath()
                                    + File.separator
                                    + transferObject.getParameter());
                    if (file.exists()
                            && Arrays.stream(this.current.listFiles())
                            .map(f -> f.getAbsolutePath())
                            .anyMatch(s -> s.equals(file.getAbsolutePath()))) {
                        if (file.isDirectory()) {
                            response = new TransferObject(
                                    Commands.ERROR.ordinal(),
                                    String.format("Указана дерриктория %s, а не файл", transferObject.getParameter()));
                        } else {
                            if (Files.isReadable(file.toPath())) {
                                try {
                                    response = new TransferObject(
                                            Commands.LOAD.ordinal(),
                                            file.getName(),
                                            Files.readAllBytes(file.toPath()));
                                } catch (IOException e) {
                                    response = new TransferObject(
                                            Commands.ERROR.ordinal(),
                                            String.format("Ошибка чтения файла %n %s", e.getMessage()));
                                    LOGGER.error(e.getMessage(), e);
                                }
                            } else {
                                response = new TransferObject(
                                        Commands.ERROR.ordinal(),
                                        String.format("Файл %s не доступен для чтения", transferObject.getParameter()));
                            }
                        }
                    } else {
                        response = new TransferObject(
                                Commands.ERROR.ordinal(),
                                String.format("Файл %s не найден", transferObject.getParameter()));
                    }
                    return response;
                });
        this.handlerMap.put(
                Commands.LOAD.ordinal(),
                transferObject -> {
                    TransferObject response;
                    try {
                            File file = new File(
                                    this.current
                                            + File.separator
                                            + transferObject.getParameter());
                            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                                fileOutputStream.write(transferObject.getData());
                                response = new TransferObject(
                                        Commands.MSG.ordinal(),
                                        String.format("Файл %s загружен на сервер :)", transferObject.getParameter())
                                );
                            }

                    } catch (IOException e) {
                        response = new TransferObject(
                                Commands.ERROR.ordinal(),
                                String.format("Ошибка загрузки файла :(%n%s", e.getMessage())
                        );
                    }
                    return response;
                });

        this.handlerMap.put(
                Commands.EXIT.ordinal(),
                transferObject -> null);
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
