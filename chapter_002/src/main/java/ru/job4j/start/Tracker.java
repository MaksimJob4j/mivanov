package ru.job4j.start;

import java.util.ArrayList;

/**
 * Tracker.
 */
public class Tracker {
    /**
     * Заявки.
     */
    private ArrayList<Item> items = new ArrayList<>();

    /**
     * добавление заявок.
     * @param item item.
     * @return item.
     */
    public Item add(Item item) {
        if (item != null) {
            this.items.add(item);
        }
        return item;
    }

    /**
     * Удаление заявок.
     * @param item item.
     */
    public void delete(Item item) {
        this.items.remove(item);
    }

    /**
     * Получение списка всех заявок.
     * @return items.
     */
    public ArrayList<Item> findAll() {
        return this.items;
    }

    /**
     * Получение списка по имени.
     * @param name name.
     * @return items.
     */
    public ArrayList<Item> findByName(String name) {
        Tracker tracker = new Tracker();
        for (Item item: this.items) {
            if (name.equals(item.getName())) {
                tracker.add(item);
            }
        }
        return tracker.items;
    }

    /**
     * Получение заявки по id.
     * @param id id.
     * @return item.
     */
    public Item findById(String id) {
        for (Item item: this.items) {
            if (id.equals(item.getId())) {
                return item;
            }
        }
        return null;
    }

    /**
     *
     * @return string.
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Item item: this.items) {
            stringBuilder.append(item);
        }
        return stringBuilder.toString();
    }

}
