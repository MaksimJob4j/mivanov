package ru.job4j.music.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Address {
    private int id;
    private String country;
    private String city;
    private String restAddress;
}
