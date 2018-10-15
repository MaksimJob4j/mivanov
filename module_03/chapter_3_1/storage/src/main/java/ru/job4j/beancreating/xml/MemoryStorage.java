package ru.job4j.beancreating.xml;

import ru.job4j.beancreating.Storage;
import ru.job4j.beancreating.User;

public class MemoryStorage implements Storage {

    public MemoryStorage() {
        System.out.println("created MemoryStorage");
    }

    @Override
    public void add(User user) {
        System.out.println("add to memory " + user);
    }
}
