package ru.job4j.carpricesprboot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.carpricesprboot.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserByLogin(String login);
}
