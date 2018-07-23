package ru.job4j.music.entities;

import lombok.*;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
    private int id;
    private String login;
    private String password;
    private Role role;
    private Address address;
    private Collection<MusicType> musicTypes;
}
