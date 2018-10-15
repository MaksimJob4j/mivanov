package ru.job4j.beancreating.xml;

import ru.job4j.beancreating.Storage;
import ru.job4j.beancreating.User;

public class UserStorageConstructor {

    private final Storage storage;

    public UserStorageConstructor(final Storage storage) {
        this.storage = storage;
        System.out.println("created UserStorageConstructor");
    }

    public void add(User user) {
        System.out.println("add to UserStorageConstructor " + user);
        this.storage.add(user);
    }
}
