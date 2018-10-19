package ru.job4j.carpricespr.items.description;

import lombok.*;
import ru.job4j.carpricespr.items.Car;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Photo {
    private Integer id;
    private String fileName;
    private byte[] fileData;
	private Car car;
}