package ru.job4j.carpricesprboot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.job4j.carpricesprboot.domain.description.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
//@ToString
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Model model;
    private Integer year;
    private Integer mileage;
    @ManyToOne
    private Body body;
    @ManyToOne
    private Color color;
    private Float volume;
    @ManyToOne
    private Transmission transmission;
    @ManyToOne
    private Engine engine;
    @ManyToOne
    private Drive drive;
    private Boolean rightWheel = false;
    private Boolean broken = false;
    private Integer ownersNum;
    private String vin;
    private Integer power;
    private String address;
    @JsonIgnore
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "car")
    private List<Photo> photos;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
    private Integer price;
    @CreationTimestamp
    private ZonedDateTime dateCreated;
    private Boolean sold = false;
}