package ru.job4j.carpricesprboot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
//@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String login;
    @JsonIgnore
    private String password;
    @ManyToOne
    private Role role;
    @JsonIgnore
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "owner")
    private List<Car> cars;

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", login='" + login + '\''
                + ", role='" + role + '\''
                + '}';
    }
}
