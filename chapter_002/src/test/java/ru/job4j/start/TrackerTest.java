package ru.job4j.start;

import org.junit.Test;
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
        assertThat(tracker.findAll()[0], is(item));
    }

    /**
     *Test update.
     */
    @Test
    public void whenUpdateItemThenTrackerHasUpdatedItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        item.setComments(new String[]{"Comment1", "Comment2"});
        assertThat(tracker.findAll()[0], is(item));
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
        Item[] items = {itemFirst, itemSecond};
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
        Item[] items = {itemFirst, itemThird};
        assertThat(tracker.findAll(), is(items));
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
