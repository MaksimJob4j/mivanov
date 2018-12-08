package ru.job4j.carpricespr.service;

import org.springframework.transaction.annotation.Transactional;
import ru.job4j.carpricespr.items.Car;
import ru.job4j.carpricespr.exceptions.NoCarException;

import java.util.List;

public interface CarService extends EntityService<Car> {

    Car findNotNull(int id) throws NoCarException;
    List<Car> findByUser(Integer userId);
    List<Car> findByUserLogin(String login);
    @Transactional
    List<Car> findCars(Integer brandId, Boolean dateFilter, Boolean photoFilter);
}
