package ru.job4j.listtomap;

import java.util.HashMap;
import java.util.List;

/**
 * .
 */
public class UserConvert {

    /**
     * .
     * @param list list.
     * @return HashMap.
     */
    public HashMap<Integer, User> process(List<User> list) {
        HashMap<Integer, User> userMap = new HashMap<>();

        for (User user: list) {
            userMap.put(user.getId(), user);
        }

        return userMap;
    }
}
