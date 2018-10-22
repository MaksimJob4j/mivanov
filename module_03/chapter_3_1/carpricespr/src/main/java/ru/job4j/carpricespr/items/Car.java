package ru.job4j.carpricespr.items;

import lombok.*;
import ru.job4j.carpricespr.items.description.*;

import javax.persistence.*;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;
    @Column(name = "year")
    private Integer year;
    @Column(name = "mileage")
    private Integer mileage;
    @ManyToOne
    @JoinColumn(name = "body_id")
    private Body body;
    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;
    @Column(name = "volume")
    private Float volume;
    @ManyToOne
    @JoinColumn(name = "transmission_id")
    private Transmission transmission;
    @ManyToOne
    @JoinColumn(name = "engine_id")
    private Engine engine;
    @ManyToOne
    @JoinColumn(name = "drive_id")
    private Drive drive;
    @Column(name = "right_wheel")
    private Boolean rightWheel;
    @Column(name = "broken")
    private Boolean broken;
    @Column(name = "owners_num")
    private Integer ownersNum;
    @Column(name = "vin")
    private String vin;
    @Column(name = "power")
    private Integer power;
    @Column(name = "address")
    private String address;
    @Transient
    private List<File> photos;
    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User owner;
    @Column(name = "price")
    private Integer price;
    @Column(name = "date_created")
    private ZonedDateTime dateCreated;
    @Column(name = "sold")
    private Boolean sold;
}