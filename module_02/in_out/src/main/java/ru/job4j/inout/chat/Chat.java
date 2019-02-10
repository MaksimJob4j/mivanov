package ru.job4j.inout.chat;

import java.time.LocalDateTime;

public class Chat {
    private final Author ie;
    private final Author user;
    private final ChatLog log;
    private ChatStatus chatStatus = ChatStatus.USER;

    public Chat(Author ie, Author user, ChatLog log) {
        this.ie = ie;
        this.user = user;
        this.log = log;
    }

    public void startChat() {
        welcomeMessage();
        ie.initAuthor();
        while (chatStatus != ChatStatus.END) {
            Message message;
            if (chatStatus == ChatStatus.IE) {
                message = getMessage(ie);
                chatStatus = ChatStatus.USER;
            } else {
                message = getMessage(user);
                if (message.getText().equals(Commands.STOP.getKeyWord())) {
                    chatStatus = ChatStatus.STOPPED;
                } else if (message.getText().equals(Commands.CONTINUE.getKeyWord())) {
                    chatStatus = ChatStatus.IE;
                } else if (message.getText().equals(Commands.END.getKeyWord())) {
                    chatStatus = ChatStatus.END;
                } else if (chatStatus != ChatStatus.STOPPED) {
                    chatStatus = ChatStatus.IE;
                }
            }
            this.log.log(message);
        }
    }

    private void welcomeMessage() {
        System.out.println("Добро пожаловать в чат!");
        System.out.println("Поддерживаются следующие поманды");
        System.out.println(Commands.getCommandsList());
        System.out.println("Введите новое сообщение.");
        this.log.log(String.format("%s -------------- Новая сессия --------------%n", LocalDateTime.now()));
    }

    private Message getMessage(Author  author) {
        return author.getMessage();
    }

    public static void main(String[] args) {
        Chat chat = new Chat(
                new IEAuthor("IE"),
                new UserAuthor("Max"),
                new ChatLog("chat.log"));
        chat.startChat();
    }
}
