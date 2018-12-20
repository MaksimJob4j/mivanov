package ru.job4j.carpricespran.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.carpricespran.items.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserByLogin(String login);
}
