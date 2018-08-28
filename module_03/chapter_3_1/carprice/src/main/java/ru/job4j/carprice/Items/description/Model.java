package ru.job4j.carprice.items.description;

import lombok.*;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Model {
    private int id;
    private String name;
    private Brand brand;
}
