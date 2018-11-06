package ru.job4j.carpricespr.dao;

import ru.job4j.carpricespr.items.Car;

import java.util.List;


public interface CarDAO extends DAO<Car> {
    List<Car> findCars(Integer brandFilter, Boolean dateFilter, Boolean photoFilter)
            throws StoreException;
}
