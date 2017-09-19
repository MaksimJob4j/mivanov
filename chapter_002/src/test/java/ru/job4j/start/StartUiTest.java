package ru.job4j.start;


import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test StartUi.
 */
public class StartUiTest {

    /**
     * Test add.
     */
    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"0", "TestName", "TestTask", "6"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findAll()[0].getName(), is("TestName"));
    }

    /**
     * Edit.
     */
    @Test
    public void whenUserChangeTaskThenTrackerHasChangedItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("TestName", "TestTask", 111L);
        tracker.add(item);
        Input input = new StubInput(new String[] {
                "2", item.getId(), "ChangedTask",
                "0", "6"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findAll()[0].getTask(), is("ChangedTask"));

    }

    /**
     * Delete.
     */
    @Test
    public void whenUserDeleteItemThenTreckerHasNoThisItem() {
        Tracker tracker = new Tracker();
        Item first = new Item("First", "FirstTestTask", 111L);
        Item second = new Item("Second", "SecondTestTask", 222L);
        tracker.add(first);
        tracker.add(second);
        Input input = new StubInput(new String[] {
                "3", first.getId(),
                "0", "6"});
        new StartUI(input, tracker).init();
        assertThat(tracker.toString(), is(second.toString()));
    }

}