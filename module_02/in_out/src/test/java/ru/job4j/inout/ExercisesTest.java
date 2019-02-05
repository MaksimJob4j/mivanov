package ru.job4j.inout;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.junit.Assert.*;

public class ExercisesTest {

    @Test
    public void whenStringNumberIsEvenThenTrue() {
        resultIsTrueString("2");
        resultIsTrueString("-2");
        resultIsTrueString("1000");
        resultIsTrueString("-10000002");
    }

    @Test
    public void whenStringNumberIsOddThenFalse() {
        resultIsFalseString("0");
        resultIsFalseString("1");
        resultIsFalseString("11111");
        resultIsFalseString("-1");
        resultIsFalseString("-11111111");
    }

    @Test(expected = NumberFormatException.class)
    public void whenStringIsNotNumberThenExceptionThrows() {
        resultIsTrueString("");
    }

    @Test(expected = NumberFormatException.class)
    public void whenStringIsNotIntegerNumberThenExceptionThrows() {
        resultIsTrueString("2.2");
    }

    @Test
    public void whenNumberIsEvenByteThenTrue() {
        resultIsTrue(new byte[]{2});
        resultIsTrue(new byte[]{126});
        resultIsTrue(new byte[]{-2});
        resultIsTrue(new byte[]{-128});
    }

    @Test
    public void whenNumberIsOddByteThenFalse() {
        resultIsFalse(new byte[]{0});
        resultIsFalse(new byte[]{1});
        resultIsFalse(new byte[]{127});
        resultIsFalse(new byte[]{-1});
        resultIsFalse(new byte[]{-127});
    }

    @Test
    public void whenNumberIsEvenIntThenTrue() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        resultIsTrue(byteBuffer.putInt(2).array());
        byteBuffer.flip();
        resultIsTrue(byteBuffer.putInt(394890876).array());
        byteBuffer.flip();
        resultIsTrue(byteBuffer.putInt(-2).array());
        byteBuffer.flip();
        resultIsTrue(byteBuffer.putInt(-394890876).array());
    }

    @Test
    public void whenNumberIsOddIntThenFalse() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        resultIsFalse(byteBuffer.putInt(0).array());
        byteBuffer.flip();
        resultIsFalse(byteBuffer.putInt(1).array());
        byteBuffer.flip();
        resultIsFalse(byteBuffer.putInt(394890875).array());
        byteBuffer.flip();
        resultIsFalse(byteBuffer.putInt(-1).array());
        byteBuffer.flip();
        resultIsFalse(byteBuffer.putInt(-394890871).array());
    }

    @Test
    public void whenNumberIsEvenLongThenTrue() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        resultIsTrue(byteBuffer.putLong(2L).array());
        byteBuffer.flip();
        resultIsTrue(byteBuffer.putLong(8888888888888888888L).array());
        byteBuffer.flip();
        resultIsTrue(byteBuffer.putLong(-2L).array());
        byteBuffer.flip();
        resultIsTrue(byteBuffer.putLong(-8888888888888888888L).array());
    }

    @Test
    public void whenNumberIsOddLongThenFalse() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        resultIsFalse(byteBuffer.putLong(0L).array());
        byteBuffer.flip();
        resultIsFalse(byteBuffer.putLong(1L).array());
        byteBuffer.flip();
        resultIsFalse(byteBuffer.putLong(5555555555555555555L).array());
        byteBuffer.flip();
        resultIsFalse(byteBuffer.putLong(-1L).array());
        byteBuffer.flip();
        resultIsFalse(byteBuffer.putLong(-5555555555555555555L).array());
    }

    @Test
    public void whenNumberIsNotStandardJavaTypeFalse() {
        resultIsFalse(new byte[]{});
        resultIsFalse(new byte[]{0, 0, 2});
        resultIsFalse(new byte[]{0, 0, 0, 2, 0, 0});
        resultIsFalse(new byte[]{0, 0, 0, 2, 0, 0, 0});
        resultIsFalse(new byte[]{0, 0, 0, 2, 0, 0, 0, 0, 0, 0});
    }

    private void resultIsTrue(byte[] bytes) {
        try (ByteArrayInputStream stream = new ByteArrayInputStream(bytes)) {
            Exercises exercises = new Exercises();
            assertTrue(exercises.isNumberByteArray(stream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resultIsFalse(byte[] bytes) {
        try (ByteArrayInputStream stream = new ByteArrayInputStream(bytes)) {
            Exercises exercises = new Exercises();
            assertFalse(exercises.isNumberByteArray(stream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resultIsTrueString(String s) {
        try (ByteArrayInputStream stream = new ByteArrayInputStream(s.getBytes())) {
            Exercises exercises = new Exercises();
            assertTrue(exercises.isNumber(stream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resultIsFalseString(String s) {
        try (ByteArrayInputStream stream = new ByteArrayInputStream(s.getBytes())) {
            Exercises exercises = new Exercises();
            assertFalse(exercises.isNumber(stream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenHaveAbusesThenOutputStreamHaveNoAbuses() {
        String inputString = "qqq ww eeee rrr qwwrr q" + System.lineSeparator() + "qqqqq ee rrr";
        try (ByteArrayInputStream is = new ByteArrayInputStream(inputString.getBytes());
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Exercises exercises = new Exercises();
            exercises.dropAbuses(is, out, new String[]{"ww", "qq"});
            String outputString = "q  eeee rrr qrr q" + System.lineSeparator() + "q ee rrr";
            assertEquals(out.toString(), outputString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenInputStreamIsAbuseThenOutputStreamIsEmpty() {
        String inputString = "qqq ww eeee rrr qwwrr q" + System.lineSeparator() + "qqqqq ee rrr";
        try (ByteArrayInputStream is = new ByteArrayInputStream(inputString.getBytes());
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Exercises exercises = new Exercises();
            exercises.dropAbuses(is, out, new String[]{inputString});
            String outputString = "";
            assertEquals(out.toString(), outputString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenHaveNoAbusesThenInputStreamEqualOutputStream() {
        String inputString = "qqq ww eeee rrr qwwrr q" + System.lineSeparator() + "qqqqq ee rrr";
        try (ByteArrayInputStream is = new ByteArrayInputStream(inputString.getBytes());
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Exercises exercises = new Exercises();
            exercises.dropAbuses(is, out, new String[]{});
            assertEquals(out.toString(), inputString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenAbusesIsNullThenInputStreamEqualOutputStream() {
        String inputString = "qqq ww eeee rrr qwwrr q" + System.lineSeparator() + "qqqqq ee rrr";
        try (ByteArrayInputStream is = new ByteArrayInputStream(inputString.getBytes());
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Exercises exercises = new Exercises();
            exercises.dropAbuses(is, out, null);
            assertEquals(out.toString(), inputString);
        } catch (IOException e) {
            e.printStackTrace();
        }
}

    @Test
    public void whenHaveAbusesThenOutputStreamHaveNoAbusesReadLineMethod() {
        String inputString = "qqq ww eeee rrr qwwrr q"
                + System.lineSeparator()
                + "qqqqq ee rrr"
                + System.lineSeparator();
        try (ByteArrayInputStream is = new ByteArrayInputStream(inputString.getBytes());
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Exercises exercises = new Exercises();
            exercises.dropAbusesLines(is, out, new String[]{"ww", "qq"});
            String outputString = "q  eeee rrr qrr q"
                    + System.lineSeparator()
                    + "q ee rrr"
                    + System.lineSeparator();
            assertEquals(out.toString(), outputString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenHaveNoAbusesThenInputStreamEqualOutputStreamReadLineMethod() {
        String inputString = "qqq ww eeee rrr qwwrr q"
                + System.lineSeparator()
                + "qqqqq ee rrr"
                + System.lineSeparator();
        try (ByteArrayInputStream is = new ByteArrayInputStream(inputString.getBytes());
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Exercises exercises = new Exercises();
            exercises.dropAbusesLines(is, out, new String[]{});
            assertEquals(inputString, out.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenAbusesIsNullThenInputStreamEqualOutputStreamReadLineMethod() {
        String inputString = "qqq ww eeee rrr qwwrr q"
                + System.lineSeparator()
                + "qqqqq ee rrr"
                + System.lineSeparator();
        try (ByteArrayInputStream is = new ByteArrayInputStream(inputString.getBytes());
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Exercises exercises = new Exercises();
            exercises.dropAbusesLines(is, out, null);
            assertEquals(inputString, out.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}