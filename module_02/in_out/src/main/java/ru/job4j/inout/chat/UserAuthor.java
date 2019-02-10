package ru.job4j.inout.chat;


import lombok.Getter;
import java.time.LocalDateTime;
import java.util.Scanner;

@Getter
public class UserAuthor extends Author {

    public UserAuthor(String name) {
        super(name);
    }

    @Override
    public Message getMessage() {
        return new Message(this, new Scanner(System.in).nextLine(), LocalDateTime.now());
    }
}
