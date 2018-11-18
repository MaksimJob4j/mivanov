package ru.job4j.carpricespr.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.carpricespr.items.description.Photo;

import java.util.List;

@Repository
public interface PhotoRepository extends CrudRepository<Photo, Integer> {
    List<Photo> findAllByOrderByFileName();
    List<Photo> findAllByCarId(Integer carId);
}
