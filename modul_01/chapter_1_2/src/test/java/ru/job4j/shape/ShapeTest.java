package ru.job4j.shape;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * test.
 */
public class ShapeTest {

    /**
     * Triangle test.
     */
    @Test
    public void whenPaintTriangleThenGetTriangle() {
        Paint paint = new Paint();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        paint.draw(new Triangle());

        String result = out.toString();
        StringBuilder stringBuilder = new StringBuilder();
        int length = 4;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < i + 1; j++) {
                stringBuilder.append("*");
            }
            stringBuilder.append(System.getProperty("line.separator"));
        }

        String expected = stringBuilder.toString();
        assertThat(result, is(expected));
    }

    /**
     * Square test.
      */
    @Test
    public void whenPaintSquareThenGetSquare() {
        Paint paint = new Paint();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        paint.draw(new Square());
        String result = out.toString();
        StringBuilder stringBuilder = new StringBuilder();
        int length = 4;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                stringBuilder.append("*");
            }
            stringBuilder.append(System.getProperty("line.separator"));
        }

        String expected = stringBuilder.toString();
        assertThat(result, is(expected));
    }

}
