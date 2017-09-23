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
    private UserAction[] actions = new UserAction[20];

    /**
     * Number of menu items.
     */
    private int position = 0;

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
     *
     * @return range.
     */
    public int[] getRange() {
        int[] range = new int[position];
        for (int i = 0; i < position; i++) {
            range[i] = this.actions[i].key();
        }
        return range;
    }

    /**
     * fillActions.
     */
    public void fillActions() {
        this.actions[position++] = this.new AddItem(position, "Add new item");
        this.actions[position++] = this.new ShowAll(position, "Show all items");
        this.actions[position++] = new MenuTracker.Edit(position, "Edit item");
        this.actions[position++] = new MenuTracker.Delete(position, "Delete item");
        this.actions[position++] = new FindById(position, "Find item by Id");
        this.actions[position++] = new FindByName(position, "Find items by name");

        BaseAction menuExit = new BaseAction("Exit") {
            @Override
            public void execute(Input input, Tracker tracker) {
            }
        };
        this.addAction(menuExit);
    }

    /**
     *
     * @param baseAction baseAction.
     */
    public void addAction(BaseAction baseAction) {
        this.actions[position++] = baseAction;
        baseAction.setKey(position);
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
    private class AddItem extends BaseAction {

        /**
         * @param key  key.
         * @param name name.
         */
        AddItem(int key, String name) {
            super(key, name);
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

    }

    /**
     * showAll.
     */
    private class ShowAll extends BaseAction {
        /**
         * @param key  key.
         * @param name name.
         */
        ShowAll(int key, String name) {
            super(key, name);
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
    }

    /**
     * Edit.
     */
    public static class Edit extends BaseAction {

        /**
         * @param key  key.
         * @param name name.
         */
        Edit(int key, String name) {
            super(key, name);
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
    }

    /**
     * Delete item.
     */
    public static class Delete extends BaseAction {
        /**
         * @param key  key.
         * @param name name.
         */
        Delete(int key, String name) {
            super(key, name);
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
    }

}

/**
 * Find by ID.
 */
class FindById extends BaseAction {

    /**
     * @param key  key.
     * @param name name.
     */
    FindById(int key, String name) {
        super(key, name);
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
}

/**
 * Find by mame.
 */
class FindByName extends BaseAction {

    /**
     * @param key  key.
     * @param name name.
     */
    FindByName(int key, String name) {
        super(key, name);
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
}
//
///**
// * Exit.
// */
//class Exit extends BaseAction {
//
//    /**
//     * @param key  key.
//     * @param name name.
//     */
//    Exit(int key, String name) {
//        super(key, name);
//    }
//
//    /**
//     *
//     * @param input input.
//     * @param  tracker tracker.
//     */
//    @Override
//    public void execute(Input input, Tracker tracker) {
//    }
//}
