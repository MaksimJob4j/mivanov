package ru.job4j.inout.chat;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IEAuthor extends Author {

    private String messageFile;
    private List<String> messages = new ArrayList<>();

    public IEAuthor(String name) {
        super(name);
    }

    public IEAuthor(String name, String messageFile) {
        super(name);
        this.messageFile = messageFile;
    }

    @Override
    public Message getMessage() {
        String textMessage = "Мне нечего сказать (";
        if (messages.size() > 0) {
            textMessage = messages.get((int) (Math.random() * messages.size()));
        }
        Message message = new Message(this, textMessage, LocalDateTime.now());
        System.out.printf("%S: %s%n", message.getAuthor().getName(), textMessage);
        return message;
    }

    @Override
    public void initAuthor() {
        updateMassageList();
    }

    public void updateMassageList() {
        try {

            Path path;
            if (this.messageFile == null) {
                path = Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                        .getResource("IEAnswers.txt")).toURI());
            } else {
                path = new File(this.messageFile).toPath();
            }
            this.messages  = Files.readAllLines(path);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    public void updateMassageList(String messageFile) {
        this.messageFile = messageFile;
        updateMassageList();
    }

}
