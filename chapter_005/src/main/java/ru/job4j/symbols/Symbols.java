package ru.job4j.symbols;

import java.util.HashMap;
import java.util.Map;

public class Symbols {
    public Boolean compareStrings(String first, String second) {
        Boolean result = true;
        if (first == null || second == null) {
            result = first == null && second == null;
        } else {
            Map<Character, Integer> counter = new HashMap<>();
            for (char c : first.toCharArray()) {
                counter.merge(c, 1, (val, newVal) -> val + 1);
            }
            for (char c : second.toCharArray()) {
                if (counter.get(c) == null) {
                    result = false;
                    break;
                } else if (counter.get(c) == 1) {
                    counter.remove(c);
                } else {
                    counter.merge(c, -1, (val, newVal) -> val - 1);
                }
            }
            if (result) {
                result = counter.size() == 0;
            }
        }
        return result;
    }
}
