package ru.job4j.music.dao;

import ru.job4j.music.entities.Role;

public interface RoleDAO extends DAO<Role> {
    @Override
    Role find(String name) throws StoreException;
}
