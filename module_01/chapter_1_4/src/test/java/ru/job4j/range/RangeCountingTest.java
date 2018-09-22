package ru.job4j.range;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class RangeCountingTest {

    @Test
    public void diapasonLine() {
        List<Double> expected = new ArrayList<>(
                Arrays.asList(1d, 2d, 3d));
        List<Double> result = new RangeCounting().diapasonLine(1, 4);
        assertThat(result, is(expected));
    }

    @Test
    public void diapasonQuadratic() {
        List<Double> expected = new ArrayList<>(
                Arrays.asList(1d, 4d, 9d));
        List<Double> result = new RangeCounting().diapasonQuadratic(1, 4);
        assertThat(result, is(expected));
    }

    @Test
    public void diapasonLogarithmic() {
        List<Double> expected = new ArrayList<>(
                Arrays.asList(Math.log(1), Math.log(2), Math.log(3)));
        List<Double> result = new RangeCounting().diapasonLogarithmic(1, 4);
        assertThat(result, is(expected));
    }
}