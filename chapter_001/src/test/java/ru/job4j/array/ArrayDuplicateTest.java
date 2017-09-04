package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 */
public class ArrayDuplicateTest {

    /**
     * Test.
     */
    @Test
    public void whenArrayDuplicateArrayWithDuplicateThenArrayWithoutDuplicate() {
        ArrayDuplicate arrayDuplicate = new ArrayDuplicate();
        String[]    expected = {"sun", "sky", "tree"},
                    array = {"sun", "sky", "tree", "sun"},
                    result = arrayDuplicate.remove(array);
        assertThat(result, is(expected));
    }

    /**
     * Test.
     */
    @Test
    public void whenArrayDuplicateArrayOfDuplicateThenArrayWithoutDuplicate() {
        ArrayDuplicate arrayDuplicate = new ArrayDuplicate();
        String[]    expected = {"sun"},
                    array = {"sun", "sun", "sun", "sun"},
                    result = arrayDuplicate.remove(array);
        assertThat(result, is(expected));
    }

    /**
     * Test.
     */
    @Test
    public void whenArrayDuplicateArrayWithoutDuplicatThenArrayWithoutDuplicate() {
        ArrayDuplicate arrayDuplicate = new ArrayDuplicate();
        String[]    expected = {"sun", "sky", "tree"},
                    array = {"sun", "sky", "tree"},
                    result = arrayDuplicate.remove(array);
        assertThat(result, is(expected));
    }
}
