package ru.job4j.beancreating.xml;

import lombok.Setter;
import ru.job4j.beancreating.Storage;
import ru.job4j.beancreating.User;

@Setter
public class UserStorageProperty {

    private Storage storage;

    public UserStorageProperty() {
        System.out.println("created UserStorageProperty");
    }

    public void add(User user) {
        System.out.println("add to UserStorage " + user);
        this.storage.add(user);
    }
}
