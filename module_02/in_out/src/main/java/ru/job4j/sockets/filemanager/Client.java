package ru.job4j.sockets.filemanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

/**
 * Реализация клиента.
 */
public class Client implements Commutator {
    private final static Logger LOGGER = LogManager.getLogger(Client.class);
    private Properties prop = new Properties();
    private String ip;
    private int port;
    private final ClientRequestHandler requestHandler = new ClientRequestHandler();

    public void init() {
        LOGGER.traceEntry();
        try (InputStream inputStream =
                     Client.class.getClassLoader().getResourceAsStream("fileManager.properties")) {
            prop.load(inputStream);
        } catch (IOException e) {
            LOGGER.error(e);
        }
        ip = prop.getProperty("ip");
        port = Integer.parseInt(prop.getProperty("port"));
        requestHandler.initCommands();
    }

    public void connect() {
        LOGGER.traceEntry();
        try (Socket socket =  new Socket(InetAddress.getByName(this.ip), this.port)) {
            start(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(Socket socket) {
        LOGGER.traceEntry();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(
                new BufferedInputStream(socket.getInputStream()));
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                     new BufferedOutputStream(socket.getOutputStream()));
                          Scanner console = new Scanner(System.in)) {
            objectOutputStream.flush();
            boolean work = true;
            do {
                TransferObject clientTransfer = null;
                try {
                    clientTransfer = TransferObject.pars(console.nextLine());
                } catch (RequestException e) {
                    System.out.println(e.getMessage());
                }
                if (clientTransfer != null) {
                    sendTransfer(objectOutputStream, clientTransfer);
                    if (Commands.EXIT.ordinal() == clientTransfer.getCommand()) {
                        work = false;
                    } else {
                        TransferObject serverTransfer = receiveTransfer(objectInputStream);
                        requestHandler.handleRequest(serverTransfer);
                    }
                }
            } while (work);
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public TransferObject receiveTransfer(ObjectInputStream in) throws IOException, ClassNotFoundException {
        LOGGER.traceEntry();
        return (TransferObject) in.readObject();
    }

    @Override
    public void sendTransfer(ObjectOutputStream out, TransferObject transfer) throws IOException {
        LOGGER.traceEntry();
        out.writeObject(transfer);
        out.flush();
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.init();
        client.connect();
    }
}