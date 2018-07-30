package ru.job4j.todolist.dao;

import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Item {
    private Integer id;
    private String task;
    private ZonedDateTime created;
    private Boolean done;
}
