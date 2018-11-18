package ru.job4j.carpricespr.service;

import ru.job4j.carpricespr.items.User;

public interface UserService extends EntityService<User> {

    User findByLogin(String login);
}
