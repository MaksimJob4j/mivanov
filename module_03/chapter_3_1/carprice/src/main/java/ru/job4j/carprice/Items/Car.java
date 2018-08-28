package ru.job4j.carprice.items;

import lombok.*;
import ru.job4j.carprice.items.description.*;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Car {
    private Integer id;
    private Model model;
    private Integer year;
    private Integer mileage;
    private Body body;
    private Color color;
    private Float volume;
    private Transmission transmission;
    private Engine engine;
    private Drive drive;
    private Boolean rightWheel;
    private Boolean broken;
    private Integer ownersNum;
    private String vin;
    private Integer power;
    private String address;
    private List<File> photos;
    private User owner;
    private Integer price;
    private ZonedDateTime dateCreated;
    private Boolean sold;
}