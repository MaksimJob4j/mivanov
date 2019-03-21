package ru.job4j.ood.menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
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

    public MenuItem getItem(List<String> keys) throws KeyMenuException {
        LOGGER.traceEntry();
        if (keys.size() == 0) {
            return this;
        } else {
            MenuItem menuItem = this.items.get(keys.remove(0));
            if (menuItem != null) {
                return menuItem.getItem(keys);
            } else {
                throw new KeyMenuException("Wrong key.");
            }
        }
    }

    private String printMenu(String keySeparator, String prevKey) {
        LOGGER.traceEntry();
        String fullKey;
        if (prevKey.isEmpty()) {
            fullKey = String.join("", " ", this.key);
        } else {
            fullKey = String.join(keySeparator, prevKey, this.key);
        }
        fullKey = String.join("", "-", fullKey);
        StringBuilder builder = new StringBuilder();
        builder.append(fullKey)
                .append(" ")
                .append(this.name)
                .append(System.lineSeparator());
        for (MenuItem menuItem: this.items.values()) {
            builder.append(menuItem.printMenu(keySeparator, fullKey));
        }
        return builder.toString();
    }

    public String printMenu(String keySeparator) {
        LOGGER.traceEntry();
        StringBuilder builder = new StringBuilder();
        for (MenuItem menuItem: this.items.values()) {
            builder.append(menuItem.printMenu(keySeparator, ""));
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
