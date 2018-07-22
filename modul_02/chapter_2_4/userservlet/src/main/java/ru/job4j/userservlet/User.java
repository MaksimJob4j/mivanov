package ru.job4j.userservlet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
//    public static final List<String> FIELDS = Arrays.asList("id", "login", "password", "name", "email", "createDate", "role");
    private String id;
    private String login;
    private String password;
    private String name;
    private String email;
    private LocalDateTime createDate;
    private String role;
    private String country;
    private String city;
}
