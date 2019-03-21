package ru.job4j.ood.menu;

public interface MenuAction {
    default void perform() throws MenuActionException {
        throw new MenuActionException("Action not defined");
    }
}
