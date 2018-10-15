package ru.job4j.beancreating.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.job4j.beancreating.Storage;
import ru.job4j.beancreating.User;

public class UserStorageAnnotation {

    @Autowired
    @Qualifier("memory")
    Storage storage;

    public UserStorageAnnotation() {
        System.out.println("created UserStorageAnnotation");
    }

    public void add(User user) {
        System.out.println("add to UserStorageAnnotation " + user);
        this.storage.add(user);
    }
}
