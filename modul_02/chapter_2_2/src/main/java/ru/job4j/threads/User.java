package ru.job4j.threads;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

public class User {

    private final int id;
    private final int amount;

    User(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    int getId() {
        return id;
    }

    int getAmount() {
        return amount;
    }
}
