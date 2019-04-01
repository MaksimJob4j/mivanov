package ru.job4j.ood.menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Menu {
    private final static Logger LOGGER = LogManager.getLogger(Menu.class);
    private final MenuItem root = new MenuItem("main", "mainMenu");

    public void addItem(MenuItem menuItem) {
        LOGGER.traceEntry();
        this.root.addSubItem(menuItem);
    }

    public String perform(String keyString) {
        LOGGER.traceEntry();
        String result = "ok";
        MenuItem menuItem = null;
        try {
            menuItem = this.root.getItem(keyString);
            menuItem.perform();
        } catch (KeyMenuException e) {
            result = String.format("%s Key \"%s\" not supported.", e.getMessage(), keyString);
            LOGGER.error(result, e);
        } catch (MenuActionException e) {
            result = String.format("%s Action \"%s\" not defined.", e.getMessage(), menuItem.getName());
            LOGGER.error(result, e);
        }
        return result;
    }

    public String printMenu() {
        LOGGER.traceEntry();
        return this.root.printMenu();
    }
}
