package ru.job4j.inout.chat;

import java.io.*;

public class ChatLog {
    private final String logFile;

    public ChatLog(String logFile) {
        this.logFile = logFile;
    }

    public void log(String logString) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            writer.write(logString);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(Message message) {
        log(message.toString());
    }
}
