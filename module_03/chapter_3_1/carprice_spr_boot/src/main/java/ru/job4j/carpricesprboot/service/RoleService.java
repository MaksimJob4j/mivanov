package ru.job4j.carpricesprboot.service;

import ru.job4j.carpricesprboot.domain.Role;

public interface RoleService extends EntityService<Role> {

    Role findByName(String name);
}
