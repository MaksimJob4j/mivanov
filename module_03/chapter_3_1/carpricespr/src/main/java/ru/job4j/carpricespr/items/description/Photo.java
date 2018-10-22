package ru.job4j.carpricespr.items.description;

import lombok.*;
import ru.job4j.carpricespr.items.Car;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_data")
    private byte[] fileData;
    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;
}