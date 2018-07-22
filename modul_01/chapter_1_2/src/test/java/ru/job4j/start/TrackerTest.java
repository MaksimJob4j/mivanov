package ru.job4j.start;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 */
public class TrackerTest {

    static String initFileName = "tracker.xml";

    /**
     *Test add.
     */
    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        Tracker tracker = new Tracker(initFileName);
        tracker.init();
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        assertThat(tracker.findById(item.getId()), is(item));
    }

    /**
     *Test update.
     */
    @Test
    public void whenUpdateItemThenTrackerHasUpdatedItem() {
        Tracker tracker = new Tracker(initFileName);
        tracker.init();
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        tracker.updateTask(item, "newTestDescription");
        assertThat(tracker.findById(item.getId()).getTask(), is("newTestDescription"));
    }

    /**
     *Test delete.
     */
    @Test
    public void whenDeleteItemThenTrackerHasNoThisItem() {
        Tracker tracker = new Tracker(initFileName);
        tracker.init();
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
    public void whenFindAllItemsThenReturnAllItems() {
        Tracker tracker = new Tracker(initFileName);
        tracker.init();
        Item itemFirst = new Item("test1", "testDescription", 123L);
        Item itemSecond = new Item("test2", "testDescription", 123L);
        tracker.add(itemFirst);
        tracker.add(itemSecond);
        assertThat(tracker.findAll().contains(itemFirst), is(true));
        assertThat(tracker.findAll().contains(itemSecond), is(true));
    }

    /**
     *Test findByName.
     */
    @Test
    public void whenFindByNameThenReturnItemsWithThisName() {
        Tracker tracker = new Tracker(initFileName);
        tracker.init();
        Item itemFirst = new Item("test1", "testDescription1", 123L);
        Item itemSecond = new Item("test2", "testDescription2", 1234L);
        Item itemThird = new Item("test1", "testDescription3", 1235L);
        tracker.add(itemFirst);
        tracker.add(itemSecond);
        tracker.add(itemThird);
        assertThat(tracker.findByName("test1").contains(itemFirst), is(true));
        assertThat(tracker.findByName("test1").contains(itemSecond), is(false));
        assertThat(tracker.findByName("test1").contains(itemThird), is(true));
    }

    /**
     *Test findById.
     */
    @Test
    public void whenFindByIdThenReturnItemWithThisId() {
        Tracker tracker = new Tracker(initFileName);
        tracker.init();
        Item itemFirst = new Item("test1", "testDescription1", 123L);
        Item itemSecond = new Item("test2", "testDescription2", 1234L);
        tracker.add(itemFirst);
        tracker.add(itemSecond);
        assertThat(tracker.findById(itemFirst.getId()), is(itemFirst));
    }

}