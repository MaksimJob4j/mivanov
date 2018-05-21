package ru.job4j.symbols;

import java.util.HashMap;
import java.util.Map;

public class Symbols {
    public Boolean compareStrings(String first, String second) {
        if (first == null && second == null) {
            return true;
        } else if (first == null || second == null) {
            return false;
        }
        Map<Character, Integer> counter = new HashMap<>();
        for (char c : first.toCharArray()) {
            counter.merge(c, 1, (val, newVal) -> val + 1);
        }
        for (char c : second.toCharArray()) {
            counter.merge(c, -1, (val, newVal) -> val - 1);
        }
        for (Integer i: counter.values()) {
            if (i != 0) {
                return false;
            }
        }
        return true;
    }
}
