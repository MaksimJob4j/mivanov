package ru.job4j.beancreating.xml;

import ru.job4j.beancreating.Storage;
import ru.job4j.beancreating.User;

public class JdbcStorage implements Storage {

    public JdbcStorage() {
        System.out.println("created JdbcStorage");
    }

    @Override
    public void add(User user) {
        System.out.println("add to JDBC " + user);
    }
}
