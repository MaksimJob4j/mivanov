package ru.job4j.start;

/**
 * MenuTracker.
 */
public class MenuTracker {
    /**
     *
     */
    private Input input;
    /**
     *
     */
    private Tracker tracker;
    /**
     *
     */
    private UserAction[] actions = new UserAction[7];

    /**
     * MenuTracker.
     * @param input input.
     * @param tracker tracker.
     */
    public MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * fillActions.
     */
    public void fillActions() {
        this.actions[0] = this.new AddItem();
        this.actions[1] = this.new ShowAll();
        this.actions[2] = new MenuTracker.Edit();
        this.actions[3] = new MenuTracker.Delete();
        this.actions[4] = new FindById();
        this.actions[5] = new FindByName();
        this.actions[6] = new Exit();
    }

    /**
     *
     * @param key key.
     */
    public void select(int key) {
        this.actions[key - 1].execute(this.input, this.tracker);
    }

    /**
     * show.
     */
    public void show() {
        for (UserAction action: this.actions) {
            if (action != null) {
                System.out.println(action.info());
            }
        }
    }

    /**
     * AddItem.
     */
    private class AddItem implements UserAction {

        /**
         *
         * @return int.
         */
        @Override
        public int key() {
            return 1;
        }

        /**
         *
         * @param input input.
         * @param  tracker tracker.
         */
        @Override
        public void execute(Input input, Tracker tracker) {
            String name = input.ask("Enter name: ");
            String task = input.ask("Enter task: ");
            tracker.add(new Item(name, task, System.currentTimeMillis()));

        }

        /**
         *
         * @return info.
         */
        @Override
        public String info() {
            return String.format("%s. %s", this.key(), "Add new item");
        }
    }

    /**
     * showAll.
     */
    private class ShowAll implements UserAction {

        /**
         *
         * @return int.
         */
        @Override
        public int key() {
            return 2;
        }

        /**
         *
         * @param input input.
         * @param  tracker tracker.
         */
        @Override
        public void execute(Input input, Tracker tracker) {
            for (Item item : tracker.findAll()) {
                System.out.println(item);
            }
        }

        /**
         *
         * @return info.
         */
        @Override
        public String info() {
            return String.format("%s. %s", this.key(), "Show all items");
        }
    }

    /**
     * Edit.
     */
    public static class Edit implements UserAction {

        /**
         *
         * @return int.
         */
        @Override
        public int key() {
            return 3;
        }

        /**
         *
         * @param input input.
         * @param  tracker tracker.
         */
        @Override
        public void execute(Input input, Tracker tracker) {
            String id;
            Item item;

            id = input.ask("Enter task's ID for update: ");
            item = tracker.findById(id);
            if (item != null) {
                System.out.println(item);
                item.setTask(input.ask("Change task: "));
                System.out.println("Task changed.");
            } else {
                System.out.println("Item not found");
            }
        }

        /**
         *
         * @return info.
         */
        @Override
        public String info() {
            return String.format("%s. %s", this.key(), "Edit item");
        }
    }

    /**
     * Delete item.
     */
    public static class Delete implements UserAction {

        /**
         *
         * @return int.
         */
        @Override
        public int key() {
            return 4;
        }

        /**
         *
         * @param input input.
         * @param  tracker tracker.
         */
        @Override
        public void execute(Input input, Tracker tracker) {
            String id;
                id = input.ask("Enter task's ID for delete");
                if (tracker.findById(id) != null) {
                    tracker.delete(tracker.findById(id));
                    System.out.println("Task deleted.");
                } else {
                    System.out.println("Item not found");
                }

        }

        /**
         *
         * @return info.
         */
        @Override
        public String info() {
            return String.format("%s. %s", this.key(), "Delete item");
        }
    }

}

/**
 * Find by ID.
 */
class FindById implements UserAction {

    /**
     *
     * @return int.
     */
    @Override
    public int key() {
        return 5;
    }

    /**
     *
     * @param input input.
     * @param  tracker tracker.
     */
    @Override
    public void execute(Input input, Tracker tracker) {
        Item item = tracker.findById(input.ask("Enter task's ID for find: "));
        if (item != null) {
            System.out.println(item);
        } else {
            System.out.println("Item not found");
        }
    }

    /**
     *
     * @return info.
     */
    @Override
    public String info() {
        return String.format("%s. %s", this.key(), "Find item by Id");
    }
}

/**
 * Find by mame.
 */
class FindByName implements UserAction {

    /**
     *
     * @return int.
     */
    @Override
    public int key() {
        return 6;
    }

    /**
     *
     * @param input input.
     * @param  tracker tracker.
     */
    @Override
    public void execute(Input input, Tracker tracker) {
        String name;
        name = input.ask("Enter name for find: ");
        if (tracker.findByName(name).length > 0) {
            for (Item item : tracker.findByName(name)) {
                System.out.println(item);
            }
        } else {
            System.out.println("Items not found");
        }
    }

    /**
     *
     * @return info.
     */
    @Override
    public String info() {
        return String.format("%s. %s", this.key(), "Find item by name");
    }
}

/**
 * Exit.
 */
class Exit implements UserAction {

    /**
     *
     * @return int.
     */
    @Override
    public int key() {
        return 7;
    }

    /**
     *
     * @param input input.
     * @param  tracker tracker.
     */
    @Override
    public void execute(Input input, Tracker tracker) {
    }

    /**
     *
     * @return info.
     */
    @Override
    public String info() {
        return String.format("%s. %s", this.key(), "Exit Program");
    }
}
