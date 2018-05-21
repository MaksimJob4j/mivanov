package ru.job4j.userservlet;

import ru.job4j.userservlet.store.UserException;
import ru.job4j.userservlet.store.ValidateService;

public class TestHelper {
    private static final ValidateService USERS = ValidateService.getInstance();

    public static User getTestUser() throws UserException {
        User user = USERS.findByLogin("test_user");
        if (user != null) {
            USERS.delete(user.getId());
        }
        user = new User();
        user.setLogin("test_user");
        user.setPassword("test_password");
        user.setRole("user");
        USERS.add(user);
        user = USERS.findByLogin("test_user");
        return user;
    }
}