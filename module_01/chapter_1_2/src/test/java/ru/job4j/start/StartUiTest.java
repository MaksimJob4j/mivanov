package ru.job4j.start;


import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Test StartUi.
 */
public class StartUiTest {

    static String initFileName = "tracker.xml";

    /**
     * Test add.
     */
    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        Tracker tracker = new Tracker(initFileName);
        tracker.init();
        String name = "TestName" + new Date().getTime();
        String task = "TestTask" + new Date().getTime();
        ArrayList<String> commands = new ArrayList<>(
                Arrays.asList(
                        "1",
                        name,
                        task,
                        "y")
        );
        Input input = new StubInput(commands);
        new StartUI(input, tracker).init();
        assertThat(tracker.findByName(name).get(0).getTask(), is(task));
    }

    /**
     * Edit.
     */
    @Test
    public void whenUserChangeTaskThenTrackerHasChangedItem() {
        Tracker tracker = new Tracker(initFileName);
        tracker.init();
        String name = "TestName" + new Date().getTime();
        String task = "TestTask" + new Date().getTime();
        Item item = new Item(name, task, 111L);
        tracker.add(item);
        String newTask = "newTask" + new Date().getTime();
        ArrayList<String> commands = new ArrayList<>(
                Arrays.asList(
                        "3",
                        item.getId().toString(),
                        newTask,
                        "y")
        );
        Input input = new StubInput(commands);
        new StartUI(input, tracker).init();
        assertThat(tracker.findById(item.getId()).getTask(), is(newTask));
    }

    /**
     * Delete.
     */
    @Test
    public void whenUserDeleteItemThenTrackerHasNoThisItem() {
        Tracker tracker = new Tracker(initFileName);
        tracker.init();
        Item item = new Item("First", "FirstTestTask", 111L);
        tracker.add(item);
        Integer id = item.getId();
        assertThat(tracker.findById(id), is(item));
        ArrayList<String> commands = new ArrayList<>(
                Arrays.asList(
                        "4",
                        id.toString(),
                        "y")
        );
        Input input = new StubInput(commands);
        new StartUI(input, tracker).init();
        assertNull(tracker.findById(id));
    }

}
