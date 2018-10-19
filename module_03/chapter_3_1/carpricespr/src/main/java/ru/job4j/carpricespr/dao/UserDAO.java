package ru.job4j.carpricespr.dao;

import ru.job4j.carpricespr.items.Car;
import ru.job4j.carpricespr.items.User;

import java.util.List;


public interface UserDAO extends DAO<User> {
    User findUserByLogin(String login) throws StoreException;
    List<Car> findUsersCars(int userId) throws StoreException;

}
