package ru.job4j.carpricespr.service;

import ru.job4j.carpricespr.items.Role;

public interface RoleService extends EntityService<Role> {

    Role findByName(String name);
}
