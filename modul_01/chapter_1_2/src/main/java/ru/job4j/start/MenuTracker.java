package ru.job4j.start;

import java.util.ArrayList;

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
    private ArrayList<UserAction> actions = new ArrayList<>();

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
    public ArrayList<Integer> getRange() {
        ArrayList<Integer> range = new ArrayList<>();
        for (UserAction userAction: this.actions) {
            range.add(userAction.key());
        }
        return range;
    }

    /**
     * Получить действие по ключу.
     * @param key ключ.
     * @return действие.
     */
    public UserAction getActionByKey(int key) {
        for (UserAction userAction: actions) {
            if (userAction.key() == key) {
                return userAction;
            }
        }
        return null;
    }

    /**
     * fillActions.
     */
    public void fillActions() {
        this.actions.add(this.new AddItem(++this.position, "Add new item"));
        this.actions.add(this.new ShowAll(++this.position, "Show all items"));
        this.actions.add(new MenuTracker.Edit(++this.position, "Edit item"));
        this.actions.add(new MenuTracker.Delete(++this.position, "Delete item"));
        this.actions.add(new FindById(++this.position, "Find item by Id"));
        this.actions.add(new FindByName(++this.position, "Find items by name"));
        this.actions.add(new MenuTracker.ClearBase(++this.position, "Clear DataBase (y)"));

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
        actions.add(baseAction);
        baseAction.setKey(++this.position);
    }

    /**
     *
     * @param key key.
     */
    public void select(int key) {
        getActionByKey(key).execute(this.input, this.tracker);
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
            item = tracker.findById(Integer.parseInt(id));
            if (item != null) {
                System.out.println(item);
//                item.setTask(input.ask("Change task: "));
                tracker.updateTask(item, input.ask("Change task: "));
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
                if (tracker.findById(Integer.parseInt(id)) != null) {
                    tracker.delete(tracker.findById(Integer.parseInt(id)));
                    System.out.println("Task deleted.");
                } else {
                    System.out.println("Item not found");
                }

        }
    }

    /**
     * Delete item.
     */
    public static class ClearBase extends BaseAction {
        /**
         * @param key  key.
         * @param name name.
         */
        ClearBase(int key, String name) {
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
                id = input.ask("Do you really want to clear the database?");
                if (id.equals("y")) {
                    tracker.clearBase();
                    System.out.println("Database cleared.");
                } else {
                    System.out.println("Clearing is canceled");
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
        Item item = tracker.findById(Integer.parseInt(input.ask("Enter task's ID for find: ")));
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
        if (tracker.findByName(name).size() > 0) {
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
