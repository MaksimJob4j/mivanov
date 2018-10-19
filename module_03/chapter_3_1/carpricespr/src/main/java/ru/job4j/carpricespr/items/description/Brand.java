package ru.job4j.carpricespr.items.description;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Brand {
    private Integer id;
    private String name;
	private List<Model> models;
}