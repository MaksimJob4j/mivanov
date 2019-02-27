package ru.job4j.sockets.filemanager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Интерфейс взаимодействия клиента и сервера.
 */
public interface Commutator {
    TransferObject receiveTransfer(ObjectInputStream inputStream) throws IOException, ClassNotFoundException;
    void sendTransfer(ObjectOutputStream objectOutputStream, TransferObject transfer) throws IOException;
}
