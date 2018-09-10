package ru.job4j.carprice.dao;

import ru.job4j.carprice.items.Car;
import ru.job4j.carprice.items.User;

import java.util.List;
import java.util.Map;


public interface CarDAO extends DAO<Car> {
    List<Car> findCars(Integer brandFilter, Boolean dateFilter, Boolean photoFilter)
            throws StoreException;
    Car createCarFromParameters(Map<String, String> parameters, User loginUser) throws StoreException;
}
