package ru.job4j.listtomap;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * .
 */
public class UserConvertTest {

    /**
     * .
     */
    @Test
    public void whenSetListThenGetMap() {
        UserConvert userConvert = new UserConvert();

        List<User> users = new ArrayList<>();
        users.add(new User(111, "Max", "Moscow"));
        users.add(new User(112, "Anna", "Chelyabisk"));
        HashMap<Integer, User> result = userConvert.process(users);

        HashMap<Integer, User> excepted = new HashMap<>();
        excepted.put(111, new User(111, "Max", "Moscow"));
        excepted.put(112, new User(112, "Anna", "Chelyabisk"));

        assertThat(result, is(excepted));


    }

}
