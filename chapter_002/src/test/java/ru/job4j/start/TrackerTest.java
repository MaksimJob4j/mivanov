package ru.job4j.start;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 */
public class TrackerTest {
    /**
     *Test add.
     */
    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        assertThat(tracker.findAll().get(0), is(item));
    }

    /**
     *Test update.
     */
    @Test
    public void whenUpdateItemThenTrackerHasUpdatedItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        item.setComments(new ArrayList<>(Arrays.asList(new String[]{"Comment1", "Comment2"})));
        assertThat(tracker.findAll().get(0), is(item));
    }

    /**
     *Test delete.
     */
    @Test
    public void whenDeleteItemThenTrackerHasNoThisItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription1", 123L);
        Item itemNull = null;
        tracker.add(item);
        tracker.delete(item);
        assertThat(tracker.findById(item.getId()), is(itemNull));
    }

    /**
     *Test findAll.
     */
    @Test
    public void whenFindAllTtemsThenReturnAllItems() {
        Tracker tracker = new Tracker();
        Item itemFirst = new Item("test1", "testDescription", 123L);
        Item itemSecond = new Item("test2", "testDescription", 123L);
        tracker.add(itemFirst);
        tracker.add(itemSecond);
        ArrayList<Item> items = new ArrayList<Item>(
                Arrays.asList(new Item[]{itemFirst, itemSecond})
        );
        assertThat(tracker.findAll(), is(items));
    }

    /**
     *Test findByName.
     */
    @Test
    public void whenFindByNameThenReturnItemsWithThisName() {
        Tracker tracker = new Tracker();
        Item itemFirst = new Item("test1", "testDescription1", 123L);
        Item itemSecond = new Item("test2", "testDescription2", 1234L);
        Item itemThird = new Item("test1", "testDescription3", 1235L);
        tracker.add(itemFirst);
        tracker.add(itemSecond);
        tracker.add(itemThird);
        ArrayList<Item> items = new ArrayList<Item>(
                Arrays.asList(new Item[]{itemFirst, itemThird})
        );

        assertThat(tracker.findByName("test1"), is(items));
    }

    /**
     *Test findById.
     */
    @Test
    public void whenFindByIdThenReturnItemWithThisId() {
        Tracker tracker = new Tracker();
        Item itemFirst = new Item("test1", "testDescription1", 123L);
        Item itemSecond = new Item("test2", "testDescription2", 1234L);
        tracker.add(itemFirst);
        tracker.add(itemSecond);
        assertThat(tracker.findById(itemFirst.getId()), is(itemFirst));
    }

}
