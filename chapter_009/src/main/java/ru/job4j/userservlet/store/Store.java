package ru.job4j.userservlet.store;

import ru.job4j.userservlet.User;

import java.util.List;

public interface Store {
    User add(User user) throws UserSaveStoreException, UserReadStoreException;
    User update(User user) throws UserUpdateStoreException, UserReadStoreException;
    User delete(String id) throws UserDeleteStoreException, UserReadStoreException;
    List<User> findAll() throws UserReadStoreException;
    User findById(String id) throws UserReadStoreException;
}
