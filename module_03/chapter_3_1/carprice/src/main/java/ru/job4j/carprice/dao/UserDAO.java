package ru.job4j.carprice.dao;

import ru.job4j.carprice.items.Car;
import ru.job4j.carprice.items.User;

import java.util.List;


public interface UserDAO extends DAO<User> {
    User findUserByLogin(String login) throws StoreException;
    List<Car> findUsersCars(int userId) throws StoreException;

}
