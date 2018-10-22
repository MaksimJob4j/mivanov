package ru.job4j.carpricespr.items.description;

import lombok.*;

import javax.persistence.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Transient
//    @OneToMany
//    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Model> models;

}