package ru.job4j.userservlet.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.userservlet.Role;
import ru.job4j.userservlet.User;

import java.util.List;

public class ValidateService {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.userservlet.store.ValidateService.class);

    private static final ValidateService INSTANCE = new ValidateService();

    private final Store store = DBStore.getInstance();

    private ValidateService() {
    }

    public static ValidateService getInstance() {
        LOGGER.traceEntry();
        return INSTANCE;
    }

    public User add(User user) throws UserException {
        LOGGER.traceEntry();
        checkUserForNull(user);
        if (user.getLogin() == null || user.getLogin().equals("")) {
            throw new UserException("Login is NULL!");
        }
        return store.add(user);
    }

    public User update(User user) throws UserException {
        LOGGER.traceEntry();
        checkUserForNull(user);
        User oldUser = findById(user.getId());
        checkUserStoreForNull(oldUser);
        if (user.getLogin() != null) {
            if (user.getLogin().equals("")) {
                throw new UserException("Login is NULL!");
            }
            oldUser.setLogin(user.getLogin());
        }
        if (user.getPassword() != null) {
            oldUser.setPassword(user.getPassword());
        }
        if (user.getName() != null) {
            oldUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            oldUser.setEmail(user.getEmail());
        }
        if (user.getRole() != null) {
            oldUser.setRole(user.getRole());
        }
        if (user.getCountry() != null) {
            oldUser.setCountry(user.getCountry());
        }
        if (user.getCity() != null) {
            oldUser.setCity(user.getCity());
        }
        return store.update(oldUser);
    }

    public User delete(String id) throws UserException {
        LOGGER.traceEntry();
        checkUserStoreForNull(store.findById(id));
        return store.delete(id);
    }

    public List<Role> findAllRoles() throws UserReadStoreException {
        LOGGER.traceEntry();
        return store.findAllRoles();
    }

    public Role findRoleById(String id) throws UserException {
        LOGGER.traceEntry();
        return store.findRoleById(id);
    }

    public List<User> findAll() throws UserReadStoreException {
        LOGGER.traceEntry();
        return store.findAll();
    }

    public User findById(String id) throws UserException {
        LOGGER.traceEntry();
        return store.findById(id);
    }

    private void checkUserForNull(User user) throws UserException {
        LOGGER.traceEntry();
        if (user == null) {
            throw new NullUserException("User is NULL.");
        }
    }

    private void checkUserStoreForNull(User user) throws UserException {
        LOGGER.traceEntry();
        if (user == null) {
            throw new NullUserStoreException("User don't found in the store.");
        }
    }

    public boolean isCredential(String login, String password) {
        LOGGER.traceEntry();
        boolean exist = false;
        try {
            User user = store.findByLogin(login);
            if (user != null && password.equals(user.getPassword())) {
                exist = true;
            }
        } catch (UserReadStoreException e) {
            e.printStackTrace();
        }
        return exist;
    }

    public User findByLogin(String login) throws UserException {
        LOGGER.traceEntry();
        return store.findByLogin(login);
    }
}