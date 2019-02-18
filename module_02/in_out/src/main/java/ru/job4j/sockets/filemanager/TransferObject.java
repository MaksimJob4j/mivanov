package ru.job4j.sockets.filemanager;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

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

    public static TransferObject pars(String request) throws RequestException {
        if (request == null || request.trim().isEmpty()) {
            throw new RequestException("Empty request.");
        } else {
            String[] req = request.trim().split(" ");
            try {
                TransferObject transferObject = new TransferObject();
                transferObject.setCommand(Commands.valueOf(req[0].toLowerCase()).ordinal());
                transferObject.setParameter(request.replaceFirst(req[0],"").trim());
                return transferObject;
            } catch (IllegalArgumentException e) {
                throw new RequestException(
                        String.format("Request syntax error. Command '%s' not supported", req[0]));
            }
        }
    }
}