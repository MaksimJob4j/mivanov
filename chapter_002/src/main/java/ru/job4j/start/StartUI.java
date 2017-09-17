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
     *
     * @param input input.
     */
    public StartUI(Input input) {
        this.input = input;
    }

    /**
     * init.
     */
    public void init() {

        Tracker tracker = new Tracker();
        String menuChoice;
        do {
            menuChoice = input.ask(MENU);
            if (ADD.equals(menuChoice)) {
                this.add(tracker);
            } else if (SHOW_ALL.equals(menuChoice)) {
                this.showAll(tracker);
            } else if (EDIT.equals(menuChoice)) {
                this.edit(tracker);
            } else if (DELETE.equals(menuChoice)) {
                this.delete(tracker);
            } else if (FIND_BY_ID.equals(menuChoice)) {
                this.findById(tracker);
            } else if (FIND_BY_NAME.equals(menuChoice)) {
                this.findByName(tracker);
            }
        } while (!EXIT.equals(menuChoice));
    }


    /**
     *
     * @param tracker tracker.
     */
    private void add(Tracker tracker) {
        String name = this.input.ask("Enter name: ");
        String task = this.input.ask("Enter task: ");
        tracker.add(new Item(name, task, System.currentTimeMillis()));

    }


    /**
     *
     * @param tracker tracker.
     */
    private void showAll(Tracker tracker) {
        for (Item item : tracker.findAll()) {
            System.out.println(item);
        }
    }

    /**
     *
     * @param tracker tracker.
     */
    private void edit(Tracker tracker) {

        String id;
        Item item;
        do {
            id = input.ask("Enter task's ID for update or 0 to exit: ");
            item = tracker.findById(id);
            if (item != null) {
                System.out.println(item);
                item.setTask(input.ask("Change task: "));
            }
        } while (!"0".equals(id));

    }

    /**
     *
     * @param tracker tracker.
     */
    private void delete(Tracker tracker) {
        String id;
        do {
            id = input.ask("Enter task's ID for delete or 0 to exit: ");
            if (tracker.findById(id) != null){
                tracker.delete(tracker.findById(id));
                System.out.println("Task deleted.");
            }
        } while (!"0".equals(id));

    }

    /**
     *
     * @param tracker tracker.
     */
    private void findById(Tracker tracker) {
        String id;
        do {
            id = input.ask("Enter task's ID for find or 0 to exit: ");
            System.out.println(tracker.findById(id) != null ? tracker.findById(id) : "Ничего не найдено");
        } while (!"0".equals(id));

    }

    /**
     *
     * @param tracker tracker.
     */
    private void findByName(Tracker tracker) {
        String name;
        do {
            name = input.ask("Enter name for find or 0 to exit: ");
            for (Item item : tracker.findByName(name)) {
                System.out.println(item);
            }
        } while (!"0".equals(name));

    }

    /**
     *
     * @param args args.
     */
    public static void main(String[] args) {
        new StartUI(new ConsoleInput()).init();

    }
}
