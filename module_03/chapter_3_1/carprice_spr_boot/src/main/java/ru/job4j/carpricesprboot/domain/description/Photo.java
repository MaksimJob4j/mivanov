package ru.job4j.carpricesprboot.domain.description;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.job4j.carpricesprboot.domain.Car;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
//@ToString
@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fileName;
    @JsonIgnore
    private byte[] fileData;
    @ManyToOne
    private Car car;

    @Override
    public String toString() {
        return "Photo{"
                + "id=" + id
                + ", fileName='" + fileName + '\''
                + '}';
    }
}