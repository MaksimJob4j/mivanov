package ru.job4j.userservlet.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.userservlet.Role;
import ru.job4j.userservlet.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryStore implements Store {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.userservlet.store.MemoryStore.class);

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
        LOGGER.traceEntry();
        user.setCreateDate(LocalDateTime.now());
        users.compute((++counter).toString(), (key, value) -> {
            user.setId(counter.toString());
            return user;
        });
        return user;
    }

    @Override
    public User update(User user) {
        LOGGER.traceEntry();
        return users.put(user.getId(), user);
    }

    @Override
    public User delete(String id) {
        LOGGER.traceEntry();
        return users.remove(id);
    }

    @Override
    public List<User> findAll() {
        LOGGER.traceEntry();
        return new ArrayList<>(users.values());
    }

    @Override
    public User findById(String id) {
        LOGGER.traceEntry();
        return users.get(id);
    }

    @Override
    public User findByLogin(String login) {
        LOGGER.traceEntry();
        for (User user : users.values()) {
            if (login.equals(user.getLogin())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public Role addRole(Role role) throws UserSaveStoreException, UserReadStoreException {
        return null;
    }

    @Override
    public List<Role> findAllRoles() throws UserReadStoreException {
        return null;
    }

    @Override
    public Role findRoleById(String id) throws UserReadStoreException {
        return null;
    }

    @Override
    public Role findRoleByName(String name) throws UserReadStoreException {
        return null;
    }
}