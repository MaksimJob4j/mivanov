package ru.job4j.userservlet;

import ru.job4j.userservlet.store.UserException;
import ru.job4j.userservlet.store.ValidateService;

class TestHelper {
    private static final ValidateService USERS = ValidateService.getInstance();

    static void getTestUser() throws UserException {
        User user = USERS.findByLogin("test_user");
        if (user != null) {
            USERS.delete(user.getId());
        }
        user = new User();
        user.setLogin("test_user");
        user.setPassword("test_password");
        user.setRole("user");
        user.setEmail("user@user.us");
        USERS.add(user);
    }
}