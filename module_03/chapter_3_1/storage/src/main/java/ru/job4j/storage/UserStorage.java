package ru.job4j.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserStorage implements Storage {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.storage.UserStorage.class);

    private final Storage storage;

    public UserStorage(final Storage storage) {
        LOGGER.traceEntry();
        this.storage = storage;
    }

    @Override
    public void create(User user) {
        LOGGER.traceEntry();
        this.storage.create(user);
    }

    @Override
    public void update(User user) {
        LOGGER.traceEntry();
        this.storage.update(user);
    }

    @Override
    public void delete(User user) {
        LOGGER.traceEntry();
        this.storage.delete(user);
    }

    @Override
    public User find(int id) {
        LOGGER.traceEntry();
        return this.storage.find(id);
    }

    @Override
    public List<User> find() {
        LOGGER.traceEntry();
        return this.storage.find();
    }
}