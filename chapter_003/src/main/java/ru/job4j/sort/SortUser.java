package ru.job4j.sort;


import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * SortUser.
 */
public class SortUser {
    /**
     * .
     * @param users ListUsers.
     * @return SetUsers
     */
    public Set<User> sort(List<User> users) {
        Set<User> sortedSet = new TreeSet<>();
        for (User user: users) {
            if (user != null) {
                sortedSet.add(user);
            }
        }
        return sortedSet;
    }
}
