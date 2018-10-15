package ru.job4j.beancreating.auto;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.job4j.beancreating.Storage;
import ru.job4j.beancreating.User;

@Component
public class UserStorageAuto {
    private final static Logger LOGGER = LogManager.getLogger(UserStorageAuto.class);

    private final Storage storage;

    @Autowired
    public UserStorageAuto(final Storage storage) {
        this.storage = storage;
        System.out.println("created UserStorageAuto");
    }

    public void add(User user) {
        System.out.println("add to UserStorageAuto " + user);
        this.storage.add(user);
    }
}
