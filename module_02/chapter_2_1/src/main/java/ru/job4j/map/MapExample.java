package ru.job4j.map;

import java.util.HashMap;
import java.util.Map;

/**
 * MapExample.
 */
public class MapExample {
    /**
     * main.
     * @param args String[]
     */
    public static void main(String[] args) {
        User user1 = new User("Max");
        User user2 = new User("Max");
        Map<User, Object> map = new HashMap<>();
        map.put(user1, null);
        map.put(user2, null);

        System.out.printf("Размер map - %s элемента \n", map.size());
        for (Map.Entry entry: map.entrySet()) {
            System.out.printf("Ключ - %S, Значение - %s \n", entry.getKey(), entry.getValue());
        }

    }
}
