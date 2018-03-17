package ru.job4j.start;


import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test StartUi.
 */
public class StartUiTest {

    static File initFile = new File("C:\\projects\\mivanov\\tracker.xml");

    /**
     * Test add.
     */
    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        Tracker tracker = new Tracker(initFile);
        tracker.init();
        ArrayList<String> commands = new ArrayList<>(
                Arrays.asList(
                        "1",
                        "TestName",
                        "TestTask",
                        "y")
        );
        Input input = new StubInput(commands);
        new StartUI(input, tracker).init();
        assertThat(tracker.findAll().get(0).getName(), is("TestName"));
    }

    /**
     * Edit.
     */
    @Test
    public void whenUserChangeTaskThenTrackerHasChangedItem() {
        Tracker tracker = new Tracker(initFile);
        tracker.init();
        Item item = new Item("TestName", "TestTask", 111L);
        tracker.add(item);
        ArrayList<String> commands = new ArrayList<>(
                Arrays.asList(
                        "3",
                        item.getId().toString(),
                        "ChangedTask",
                        "y")
        );
        Input input = new StubInput(commands);
        new StartUI(input, tracker).init();
        assertThat(tracker.findAll().get(0).getTask(), is("ChangedTask"));
    }

    /**
     * Delete.
     */
    @Test
    public void whenUserDeleteItemThenTrackerHasNoThisItem() {
        Tracker tracker = new Tracker(initFile);
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
        assertThat(tracker.findById(id) == null, is(true));
    }

}
