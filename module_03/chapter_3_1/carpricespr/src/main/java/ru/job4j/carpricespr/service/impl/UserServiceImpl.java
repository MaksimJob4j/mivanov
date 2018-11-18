package ru.job4j.carpricespr.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carpricespr.items.User;
import ru.job4j.carpricespr.repository.UserRepository;
import ru.job4j.carpricespr.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final static Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void create(User user) {
        userRepository.save(user);
    }

    @Override
    public Iterable<User> find() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findOrdered() {
        return null;
    }

    @Override
    public Optional<User> find(int id) {
        return userRepository.findById(id);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User findByLogin(String login) {
        LOGGER.traceEntry();
        return userRepository.findUserByLogin(login);
    }
}
