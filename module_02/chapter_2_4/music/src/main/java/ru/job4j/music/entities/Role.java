package ru.job4j.music.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Role {
    private int id;
    private String name;
    private int level;
}
