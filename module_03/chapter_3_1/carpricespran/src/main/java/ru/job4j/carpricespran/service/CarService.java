package ru.job4j.carpricespran.service;

import org.springframework.transaction.annotation.Transactional;
import ru.job4j.carpricespran.exceptions.NoCarException;
import ru.job4j.carpricespran.items.Car;

import java.util.List;

public interface CarService extends EntityService<Car> {

    Car findNotNull(int id) throws NoCarException;
    List<Car> findByUser(Integer userId);
    List<Car> findByUserLogin(String login);
    @Transactional
    List<Car> findCars(Integer brandId, Boolean dateFilter, Boolean photoFilter);
}
