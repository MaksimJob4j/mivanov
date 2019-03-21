package ru.job4j.ood.menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class Menu {
    private final static Logger LOGGER = LogManager.getLogger(Menu.class);
    private final MenuItem mainMenuItem = new MenuItem("main", "mainMenu");
    private final String keySeparator;

    public Menu(String keySeparator) {
        this.keySeparator = keySeparator;
    }

    public void addItem(MenuItem menuItem) {
        LOGGER.traceEntry();
        this.mainMenuItem.addSubItem(menuItem);
    }

    public String perform(String keyString) {
        LOGGER.traceEntry();
        String result = "ok";
        List<String> keys = Arrays.stream(keyString.split("[" + keySeparator + "]")).collect(Collectors.toList());
        MenuItem menuItem = null;
        try {
            menuItem = this.mainMenuItem.getItem(keys);
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
        return this.mainMenuItem.printMenu(this.keySeparator);
    }
}
