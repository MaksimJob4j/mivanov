package ru.job4j.carpricespr.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.carpricespr.items.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserByLogin(String login);
}
