package ru.job4j.sockets.filemanager;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Интерфейс взаимодействия клиента и сервера.
 */
public interface Commutator {
    TransferObject receiveTransfer(ObjectInputStream inputStream);
    void sendTransfer(TransferObject transfer, ObjectOutputStream objectOutputStream);
}
