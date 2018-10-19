package ru.job4j.carpricespr.items;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class User {
    private Integer id;
    private String login;
    private String password;
    private List<Car> cars;
}
