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
    private Integer id;
    private String name;
    private Brand brand;
}
