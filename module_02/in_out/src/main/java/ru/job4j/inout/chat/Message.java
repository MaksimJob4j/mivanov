package ru.job4j.inout.chat;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Message {

    private final Author author;
    private final String text;
    private final LocalDateTime date;


    public Message(Author author, String text, LocalDateTime date) {
        this.author = author;
        this.text = text;
        this.date = date;
    }

    public String toString() {
        return String.format("%s %s - %s%n", date.toString(), author.getName(), text);
    }
}
