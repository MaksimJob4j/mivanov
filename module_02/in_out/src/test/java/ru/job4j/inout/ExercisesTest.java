package ru.job4j.inout;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;

import static org.junit.Assert.*;

public class ExercisesTest {

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
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        Exercises exercises = new Exercises();
        assertTrue(exercises.isNumber(stream));
    }

    private void resultIsFalse(byte[] bytes) {
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        Exercises exercises = new Exercises();
        assertFalse(exercises.isNumber(stream));
    }
}