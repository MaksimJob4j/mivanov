package ru.job4j.beancreating.auto;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.job4j.beancreating.Storage;
import ru.job4j.beancreating.User;

@Component
public class MemoryStorageAuto implements Storage {
    private final static Logger LOGGER = LogManager.getLogger(MemoryStorageAuto.class);

    public MemoryStorageAuto() {
        System.out.println("created MemoryStorageAuto");
    }

    @Override
    public void add(User user) {
        System.out.println("add to memory " + user);
    }
}
