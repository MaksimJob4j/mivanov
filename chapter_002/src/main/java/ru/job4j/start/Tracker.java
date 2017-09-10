package ru.job4j.start;

/**
 * Tracker.
 */
public class Tracker {
    /**
     * Заявки.
     */
    private Item[] items = new Item[100];
    /**
     * Количество заявок.
     */
    private int position = 0;

    /**
     * добавление заявок.
     * @param item item.
     * @return item.
     */
    public Item add(Item item) {
        if (item != null) {
            this.items[this.position++] = item;
        }
        return item;
    }

    /**
     * Редактирование заявок.
     * @param item item.
     */
    public void update(Item item) {
        for (int i = 0; i < this.position; i++) {
            if (item.getId().equals(this.items[i].getId())) {
                this.items[i] = item;
            }
        }

    }

    /**
     * Удаление заявок.
     * @param item item.
     */
    public void delete(Item item) {
        for (int i = 0; i < this.position; i++) {
            if (item.getId().equals(this.items[i].getId())) {
                System.arraycopy(this.items, i + 1, this.items, i, this.position - i - 1);
                i--;
                this.position--;
                this.items[this.position] = null;
            }
        }
    }

    /**
     * Получение списка всех заявок.
     * @return items.
     */
    public Item[] findAll() {
        Item[] itemsToReturn = new Item[this.position];
        System.arraycopy(this.items, 0, itemsToReturn, 0, this.position);
        return itemsToReturn;
    }

    /**
     * Получение списка по имени.
     * @param key key.
     * @return items.
     */
    public Item[] findByName(String key) {
        Tracker tracker = new Tracker();
        for (int i = 0; i < this.position; i++) {
            if (key.equals(this.items[i].getName())) {
                tracker.add(this.items[i]);
            }
        }
        return tracker.findAll();
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
}
