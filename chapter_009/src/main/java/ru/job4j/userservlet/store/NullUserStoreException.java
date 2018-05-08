package ru.job4j.userservlet.store;

class NullUserStoreException extends UserException {
    NullUserStoreException(String msg) {
        super(msg);
    }
}
