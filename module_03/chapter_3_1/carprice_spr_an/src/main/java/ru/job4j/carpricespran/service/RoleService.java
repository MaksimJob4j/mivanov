package ru.job4j.carpricespran.service;

import ru.job4j.carpricespran.items.Role;

public interface RoleService extends EntityService<Role> {

    Role findByName(String name);
}
