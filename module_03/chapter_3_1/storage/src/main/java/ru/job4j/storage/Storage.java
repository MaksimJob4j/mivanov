package ru.job4j.storage;

import java.util.List;

public interface Storage {
    void create(User user);
    void update(User user);
    void delete(User user);
    User find(int id);
    List<User> find();
}
