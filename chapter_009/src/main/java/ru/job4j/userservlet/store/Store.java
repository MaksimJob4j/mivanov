package ru.job4j.userservlet.store;

import ru.job4j.userservlet.Role;
import ru.job4j.userservlet.User;

import java.util.List;

public interface Store {
    User add(User user) throws UserSaveStoreException, UserReadStoreException;
    User update(User user) throws UserUpdateStoreException, UserReadStoreException;
    User delete(String id) throws UserDeleteStoreException, UserReadStoreException;
    List<User> findAll() throws UserReadStoreException;
    User findById(String id) throws UserReadStoreException;
    User findByLogin(String login) throws UserReadStoreException;
    public Role addRole(Role role) throws UserSaveStoreException, UserReadStoreException;
    List<Role> findAllRoles() throws UserReadStoreException;
    Role findRoleById(String id) throws UserReadStoreException;
    Role findRoleByName(String name) throws UserReadStoreException;
}
