package ru.job4j.start;

/**
 * StartUI.
 */
public class StartUI {
    /**
     * add.
     */
    private static final String ADD = "0";
    /**
     * show all.
     */
    private static final String SHOW_ALL = "1";
    /**
     * edit.
     */
    private static final String EDIT = "2";
    /**
     * delete.
     */
    private static final String DELETE = "3";
    /**
     * find by id.
     */
    private static final String FIND_BY_ID = "4";
    /**
     * find by name.
     */
    private static final String FIND_BY_NAME = "5";
    /**
     * exit.
     */
    private static final String EXIT = "6";
    /**
     * menu.
     */
    private static final String MENU = (
                    ADD + ". Add new Item" + System.getProperty("line.separator")
                    + SHOW_ALL + ". Show all items" + System.getProperty("line.separator")
                    + EDIT + ". Edit item" + System.getProperty("line.separator")
                    + DELETE + ". Delete item" + System.getProperty("line.separator")
                    + FIND_BY_ID + ". Find item by Id" + System.getProperty("line.separator")
                    + FIND_BY_NAME + ". Find items by name" + System.getProperty("line.separator")
                    + EXIT + ". Exit Program" + System.getProperty("line.separator")
                    + "Select: ");

    /**
     * input.
     */
    private Input input;

    /**
     * Tracker.
     */
    private Tracker tracker;

    /**
     *
     * @param input input.
     * @param tracker tracker.
     */
    public StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * init.
     */
    public void init() {

        MenuTracker menuTracker = new MenuTracker(this.input, this.tracker);
        menuTracker.fillActions();
        do {
            menuTracker.show();
            menuTracker.select(Integer.parseInt(input.ask("Select:")));

        } while (!"y".equals(this.input.ask("Exit? (y):")));

    }

    /**
     *
     * @param args args.
     */
    public static void main(String[] args) {
        new StartUI(new ConsoleInput(), new Tracker()).init();

    }
}


