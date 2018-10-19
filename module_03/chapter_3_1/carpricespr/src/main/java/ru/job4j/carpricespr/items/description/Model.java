package ru.job4j.carpricespr.items.description;

import lombok.*;

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
