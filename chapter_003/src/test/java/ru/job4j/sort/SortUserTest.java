package ru.job4j.sort;

import org.junit.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * .
 */
public class SortUserTest {

    /**
     * .
     */
    @Test
    public void whenSortUsersThenGetSortedSet() {
        SortUser sortUser = new SortUser();
        List<User> users = new ArrayList<User>(
                Arrays.asList(
                        new User("Max", 35),
                        new User("Den", 15),
                        new User("Alex", 20),
                        new User("Alex", 20),
                        null,
                        new User("Denis", 20)
                )
        );
        Set<User> resultSet = sortUser.sort(users);
        String result = resultSet.toString();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(new User("Den", 15));
        stringBuilder.append(", ");
        stringBuilder.append(new User("Alex", 20));
        stringBuilder.append(", ");
        stringBuilder.append(new User("Denis", 20));
        stringBuilder.append(", ");
        stringBuilder.append(new User("Max", 35));
        stringBuilder.append("]");
        String expected = stringBuilder.toString();

        assertThat(result, is(expected));

    }
}
