package ru.job4j.start;

/**
 * UserAction.
 */
public interface UserAction {

    /**
     *
     * @return key.
     */
    int key();

    /**
     *
     * @param input input.
     * @param tracker tracker.
     */
    void execute(Input input, Tracker tracker);

    /**
     *
     * @return info.
     */
    String info();

}
