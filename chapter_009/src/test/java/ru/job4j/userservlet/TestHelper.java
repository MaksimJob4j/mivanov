package ru.job4j.userservlet;

import ru.job4j.userservlet.store.UserException;
import ru.job4j.userservlet.store.ValidateService;

public class TestHelper {
    private static final ValidateService USERS = ValidateService.getInstance();

    public static User getTestUser() {
        User user = null;
        try {
            user = USERS.findByLogin("test_user");
            if (user != null) {
                USERS.delete(user.getId());
            }
        } catch (UserException e) {
            e.printStackTrace();
        }

        user = new User();
        user.setLogin("test_user");
        user.setPassword("test_password");
        user.setRole("user");
        try {
            USERS.add(user);
        } catch (UserException e) {
            e.printStackTrace();
        }

        try {
            user = USERS.findByLogin("test_user");
        } catch (UserException e) {
            e.printStackTrace();
        }

        return user;
    }
}
