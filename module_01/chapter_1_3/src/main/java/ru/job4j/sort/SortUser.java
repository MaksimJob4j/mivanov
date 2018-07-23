package ru.job4j.sort;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Comparator;

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

// В классе SortUser из 1-го задания необходимо написать два метода:
//
// 1) public List<User> sortNameLength (List<User>)
// - в этом методе необходимо определить Comparator для метода Collections.sort
// и отсортировать List<User> по длине имени.
//
// 2) public List<User> sortByAllFields (List<User>)
// - в этом методе необходимо определить Comparator для метода Collections.sort
// и отсортировать List<User> по 2-м полям, сначала проверка по имени, потом по возрасту.

    /**
     * .
     * @param users users.
     * @return List.
     */
    public List<User> sortNameLength(List<User> users) {
        List<User> sortedList = new ArrayList<>(users);
        sortedList.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                int rsl;
                if (o1 == null && o2 == null) {
                    rsl = 0;
                } else if (o1 == null) {
                    rsl = -1;
                } else if (o2 == null) {
                    rsl = 1;
                } else {
                    rsl = o1.getName().length() - o2.getName().length();
                }
                return rsl;
            }
        });
        return sortedList;
    }

    /**
     * .
     * @param users users.
     * @return List.
     */
    public List<User> sortByAllFields(List<User> users) {
        List<User> sortedList = new ArrayList<>(users);
        sortedList.sort(new Comparator<User>() {

            @Override
            public int compare(User o1, User o2) {
                int rsl;
                if (o1 == null && o2 == null) {
                    rsl = 0;
                } else if (o1 == null) {
                    rsl = -1;
                } else if (o2 == null) {
                    rsl = 1;
                } else {
                    rsl = o1.getName().compareTo(o2.getName());
                    rsl = rsl != 0 ? rsl : o1.getAge() - o2.getAge();
                }
                return rsl;
            }
        });
        return sortedList;
    }

}
