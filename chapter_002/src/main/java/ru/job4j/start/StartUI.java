package ru.job4j.start;

/**
 * StartUI.
 */
public class StartUI {

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
        int[] range = menuTracker.getRange();

        do {
            menuTracker.show();
            menuTracker.select(input.ask("Select:", range));
        } while (!"y".equals(this.input.ask("Exit? (y):")));
    }

    /**
     *
     * @param args args.
     */
    public static void main(String[] args) {
        new StartUI(new ValidateInput(), new Tracker()).init();
    }
}


