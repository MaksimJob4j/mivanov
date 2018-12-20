package ru.job4j.carpricespran.items.description;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.job4j.carpricespran.items.Car;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
//@ToString
@Entity
@Table(name = "photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "file_name")
    private String fileName;
    @JsonIgnore
    @Column(name = "file_data")
    private byte[] fileData;
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @Override
    public String toString() {
        return "Photo{"
                + "id=" + id
                + ", fileName='" + fileName + '\''
                + '}';
    }
}