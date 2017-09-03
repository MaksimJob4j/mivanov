package ru.job4j.loop;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест.
 */
public class PaintTest {

    /**
     * Test.
     */
    @Test
    public void whenPiramidWithHeightTwoThenStringWithTwoRows() {
        Paint paint = new Paint();
        String result = paint.piramid(2);
        String expected = String.format(" ^ %s^^^", System.getProperty("line.separator"));
        assertThat(result, is(expected));
    }

    /**
     * Тест.
     */
    @Test
    public void whenPiramidWithHeightThreeThenStringWithThreeRows() {
        Paint paint = new Paint();
        String result = paint.piramid(3);
        String line = System.getProperty("line.separator");
        String expected = String.format("  ^  %s ^^^ %s^^^^^", line, line);
        assertThat(expected, is(result));
    }

}
