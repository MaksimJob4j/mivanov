package ru.job4j.carpricespr.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.carpricespr.items.Car;

import java.util.List;

@Repository
public interface CarRepository extends CrudRepository<Car, Integer> {

    List<Car> findAllByOrderByDateCreatedDesc();
    List<Car> findAllByOwnerIdOrderByDateCreatedDesc(Integer useId);
    List<Car> findAllByOwnerLoginOrderByDateCreatedDesc(String login);
    Car findTopByOrderByDateCreatedDesc();
}
