package ru.job4j.ood.menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

public class MenuItem implements MenuAction {
    private final static Logger LOGGER = LogManager.getLogger(MenuItem.class);

    private final String key;
    private final String name;
    private MenuAction action;
    private final Map<String, MenuItem> items = new TreeMap<>();

    public String getKey() {
        return this.key;
    }

    public String getName() {
        return this.name;
    }

    public MenuAction getAction() {
        return this.action;
    }

    public void setAction(MenuAction action) {
        this.action = action;
    }

    public Map<String, MenuItem> getItems() {
        return this.items;
    }

    public MenuItem(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public void addSubItem(MenuItem menuItem) {
        this.items.put(menuItem.getKey(), menuItem);
    }

    public MenuItem getItem(String key) throws KeyMenuException {
        LOGGER.traceEntry();
        String currentKey = key.split("[.]")[0];
        MenuItem menuItem = this.items.get(currentKey);
        if (menuItem != null) {
            return key.equals(currentKey)
                    ? menuItem
                    : menuItem.getItem(key.substring(currentKey.length() + 1));
        }
        throw new KeyMenuException("Wrong key.");
    }

    private String printMenu(String prevKey) {
        LOGGER.traceEntry();
        String fullKey;
        if (prevKey.isEmpty()) {
            fullKey = String.join("", " ", this.key);
        } else {
            fullKey = String.join(".", prevKey, this.key);
        }
        fullKey = String.join("", "-", fullKey);
        StringBuilder builder = new StringBuilder();
        builder.append(fullKey)
                .append(" ")
                .append(this.name)
                .append(System.lineSeparator());
        for (MenuItem menuItem: this.items.values()) {
            builder.append(menuItem.printMenu(fullKey));
        }
        return builder.toString();
    }

    public String printMenu() {
        LOGGER.traceEntry();
        StringBuilder builder = new StringBuilder();
        for (MenuItem menuItem: this.items.values()) {
            builder.append(menuItem.printMenu(""));
        }
        return builder.toString();
    }

    @Override
    public void perform() throws MenuActionException {
        LOGGER.traceEntry();
        if (this.action != null) {
            this.action.perform();
        } else {
            throw new MenuActionException("Action not defined.");
        }
    }
}
