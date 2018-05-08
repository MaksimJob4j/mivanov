package ru.job4j.userservlet.store;

class NullUserException extends UserException {
    NullUserException(String msg) {
        super(msg);
    }
}
