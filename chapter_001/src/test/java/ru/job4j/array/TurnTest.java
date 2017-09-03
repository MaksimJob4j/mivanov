package ru.job4j.array;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * TurnTest.
 */
public class TurnTest {
    /**
     * Test.
     */
    @Test
    public void whenTurnArrayWithEvenAmountOfElementsThenTurnedArray() {
        Turn turn = new Turn();
        int[]   array = {7, 56, 23, 1},
                result = {1, 23, 56, 7},
                expected = turn.back(array);
        assertThat(result, is(expected));
    }

    /**
     * Test.
     */
    @Test
    public void whenTurnArrayWithOddAmountOfElementsThenTurnedArray() {
        Turn turn = new Turn();
        int[]   array = {7, 56, 23, 1, 15},
                result = {15, 1, 23, 56, 7},
                expected = turn.back(array);
        assertThat(result, is(expected));
    }

}