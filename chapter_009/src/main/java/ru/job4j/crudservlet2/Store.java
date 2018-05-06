package ru.job4j.crudservlet2;

import java.util.List;

public interface Store {
    User add(User user);
    User update(User user);
    User delete(String id);
    List<User> findAll();
    User findById(String id);
}
