package ru.job4j.userservlet.store;

import ru.job4j.userservlet.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryStore implements Store {

    private static final MemoryStore INSTANCE = new MemoryStore();

    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
    private Integer counter = 0;

    private MemoryStore() {
    }

    public static MemoryStore getInstance() {
        return INSTANCE;
    }

    @Override
    public User add(User user) {
        user.setCreateDate(LocalDateTime.now());
        users.compute((++counter).toString(), (key, value) -> {
            user.setId(counter.toString());
            return user;
        });
        return user;
    }

    @Override
    public User update(User user) {
        return users.put(user.getId(), user);
    }

    @Override
    public User delete(String id) {
        return users.remove(id);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User findById(String id) {
        return users.get(id);
    }
}