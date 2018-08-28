package ru.job4j.carprice.dao;

import ru.job4j.carprice.items.Car;
import ru.job4j.carprice.items.User;

import java.util.List;


public interface UserDAO extends DAO<User> {
    public User findUserByLogin(String login) throws StoreException;
    public List<Car> findUsersCars(int userId) throws StoreException;

}
