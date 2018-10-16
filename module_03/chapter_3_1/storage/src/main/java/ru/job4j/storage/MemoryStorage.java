package ru.job4j.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryStorage implements Storage {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.storage.MemoryStorage.class);

    private final ConcurrentHashMap<Integer, User> storage = new ConcurrentHashMap<>();
    private int currentId = 0;

    @Override
    public void create(User user) {
        LOGGER.traceEntry();
        if (user != null) {
            synchronized (this) {
                user.setId(++currentId);
            }
            this.storage.put(user.getId(), user);
        }
    }

    @Override
    public void update(User user) {
        LOGGER.traceEntry();
        if (user != null) {
            this.storage.computeIfPresent(user.getId(), (id, u) -> user);
        }
    }

    @Override
    public User find(int id) {
        LOGGER.traceEntry();
        return this.storage.get(id);
    }

    @Override
    public List<User> find() {
        LOGGER.traceEntry();
        return new ArrayList<>(this.storage.values());
    }

    @Override
    public void delete(User user) {
        LOGGER.traceEntry();
        if (user != null) {
            this.storage.remove(user.getId());
        }
    }
}
