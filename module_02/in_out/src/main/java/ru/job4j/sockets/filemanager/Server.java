package ru.job4j.sockets.filemanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class Server implements Commutator {
    private final static Logger LOGGER = LogManager.getLogger(Server.class);
    private Properties prop = new Properties();
    private int port;
    private ServerRequestHandler requestHandler;

    public void init() {
        LOGGER.traceEntry();
        try (InputStream inputStream =
                     Server.class.getClassLoader().getResourceAsStream("fileManager.properties")) {
            prop.load(inputStream);
        } catch (IOException e) {
            LOGGER.error(e);
        }
        port = Integer.parseInt(prop.getProperty("port"));
        this.requestHandler = new ServerRequestHandler(
                new File(prop.getProperty("rootDir")));
        requestHandler.initCommands();
    }

    public void connect() {
        LOGGER.traceEntry();
        try (Socket socket =  new ServerSocket(this.port).accept()) {
            start(socket);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void start(Socket socket) {
        LOGGER.traceEntry();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new BufferedOutputStream(socket.getOutputStream()));
             BufferedInputStream bufferedInputStream = new BufferedInputStream(
                     socket.getInputStream()
             )
        ) {
            objectOutputStream.flush();
            ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
            boolean work = true;
            do {
                TransferObject clientTransfer = receiveTransfer(objectInputStream);
                if (Commands.values()[clientTransfer.getCommand()] == Commands.EXIT) {
                    work = false;
                } else {
                    TransferObject serverTransfer = requestHandler.handleRequest(clientTransfer);
                    sendTransfer(objectOutputStream, serverTransfer);
                }
            } while (work);
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
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
        Server server = new Server();
        server.init();
        server.connect();
    }

}
