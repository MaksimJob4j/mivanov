package ru.job4j;

import java.util.HashMap;
import java.util.Map;

public class MapArray {
    public static void main(String[] args) {
        Map<Integer[], Integer> map = new HashMap<>();
        Integer[] array = new Integer[10];
        map.put(array, 1);
        System.out.println(map.get(array));
        array[0] = 1;
        System.out.println(map.get(array));
        array = new Integer[20];
        System.out.println(map.get(array));
        map.put(array, 2);
        System.out.println(map.size());



    }
}
