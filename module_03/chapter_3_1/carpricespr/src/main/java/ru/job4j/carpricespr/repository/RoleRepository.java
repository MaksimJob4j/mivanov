package ru.job4j.carpricespr.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.carpricespr.items.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findRoleByName(String name);
}
