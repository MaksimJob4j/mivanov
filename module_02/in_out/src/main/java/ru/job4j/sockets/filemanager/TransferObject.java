package ru.job4j.sockets.filemanager;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;

/**
 * Класс объекта запроса и ответа для коммуникации между клиентом и сервером.
 */
@Getter
@Setter
@NoArgsConstructor
public class TransferObject implements Serializable {
    private final static Logger LOGGER = LogManager.getLogger(TransferObject.class);

    private int command = -1;
    private String parameter;
    private byte[] data;

    public TransferObject(int command, String parameter) {
        this.command = command;
        this.parameter = parameter;
    }

    public TransferObject(int command, String parameter, byte[] data) {
        this.command = command;
        this.parameter = parameter;
        this.data = data;
    }

    public static TransferObject pars(String request) throws RequestException {
        LOGGER.traceEntry();
        if (request == null || request.trim().isEmpty()) {
            throw new RequestException("Empty request.");
        } else {
            String[] req = request.trim().split(" ");
            try {
                TransferObject transferObject = new TransferObject();
                transferObject.setCommand(Commands.valueOf(req[0].toUpperCase()).ordinal());
                transferObject.setParameter(request.replaceFirst(req[0], "").trim());
                if (transferObject.getCommand() == Commands.LOAD.ordinal()) {
                    try {
                        File file = new File(transferObject.getParameter());
                        if (file.exists() && !file.isDirectory()) {
                            if (Files.isReadable(file.toPath())) {
                                transferObject.setData(Files.readAllBytes(file.toPath()));
                                transferObject.setParameter(file.getName());
                            } else {
                                System.out.println(String.format("Нет доступа к файлу %s%n", transferObject.getParameter()));
                                transferObject = null;
                            }
                        } else {
                            System.out.println(String.format("Файла %s не существует %n", transferObject.getParameter()));
                            transferObject = null;
                        }
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage(), e);
                        System.out.println(String.format("Ошибка доступа к файлу%%n%s%n", e.getMessage()));
                        transferObject = null;
                    }
                }
                return transferObject;
            } catch (IllegalArgumentException e) {
                throw new RequestException(
                        String.format("Request syntax error. Command '%s' not supported", req[0]));
            }
        }
    }
}