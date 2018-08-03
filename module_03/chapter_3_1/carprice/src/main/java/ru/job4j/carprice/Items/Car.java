package ru.job4j.carprice.items;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Car {
    private Integer id;
    private String model;
    private Body body;
    private Engine engine;
	private Transmission transmission;
}