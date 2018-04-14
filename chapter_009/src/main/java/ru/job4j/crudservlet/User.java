package ru.job4j.crudservlet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class User {
    static final List<String> FIELDS = Arrays.asList("id", "name", "login", "email", "createDate");
    private String id;
    private String name;
    private String login;
    private String email;
    private LocalDateTime createDate;
}
