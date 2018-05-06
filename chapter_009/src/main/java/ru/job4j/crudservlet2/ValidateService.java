package ru.job4j.crudservlet2;

import java.util.List;

public class ValidateService {

    private static final ValidateService INSTANCE = new ValidateService();

    private final Store store = MemoryStore.getInstance();

    private ValidateService() {
    }

    public static ValidateService getInstance() {
        return INSTANCE;
    }

    User add(User user) throws UserException {
        checkUserForNull(user);
        return store.add(user);
    }

    User update(User user) throws UserException {
        checkUserForNull(user);
        User oldUser = this.findById(user.getId());
        oldUser.setName(user.getName());
        oldUser.setLogin(user.getLogin());
        oldUser.setEmail(user.getEmail());
        return store.update(oldUser);
    }

    User delete(String id) throws UserException {
        User user = store.delete(id);
        checkUserStoreForNull(user);
        return user;
    }

    List<User> findAll() {
        return store.findAll();
    }

    User findById(String id) throws UserException {
        User user = store.findById(id);
        checkUserStoreForNull(user);
        return user;
    }

    private void checkUserForNull(User user) throws UserException {
        if (user == null) {
            throw new NullUserException("User is NULL");
        }
    }

    private void checkUserStoreForNull(User user) throws UserException {
        if (user == null) {
            throw new NullUserStoreException("User don't found in the store");
        }
    }
}

class UserException extends Exception {
    UserException(String msg) {
        super(msg);
    }
}

class NullUserException extends UserException {
    NullUserException(String msg) {
        super(msg);
    }
}

class NullUserStoreException extends UserException {
    NullUserStoreException(String msg) {
        super(msg);
    }
}

