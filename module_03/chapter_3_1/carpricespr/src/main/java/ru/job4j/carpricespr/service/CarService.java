package ru.job4j.carpricespr.service;

import org.springframework.transaction.annotation.Transactional;
import ru.job4j.carpricespr.items.Car;
import ru.job4j.carpricespr.store.NoCarException;

import java.util.List;

public interface CarService extends EntityService<Car> {

    Car findNotNull(int id) throws NoCarException;
    List<Car> findByUser(Integer userId);
    @Transactional
    List<Car> findCars(Integer brandId, Boolean dateFilter, Boolean photoFilter);
}
