package ru.job4j.beancreating.config;

import lombok.Setter;
import ru.job4j.beancreating.Storage;
import ru.job4j.beancreating.User;

@Setter
public class UserStorageConfig {

    private Storage storage;

    public UserStorageConfig(Storage storage) {
        System.out.println("created UserStorageConfig");
        this.storage = storage;
    }

    public void add(User user) {
        System.out.println("add to UserStorageConfig " + user);
        this.storage.add(user);
    }
}
