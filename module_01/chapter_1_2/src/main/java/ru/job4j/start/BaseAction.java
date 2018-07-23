package ru.job4j.start;

/**
 * BaseAction.
 */
public abstract class BaseAction implements UserAction {

    /**
     * name.
     */
    private String name;
    /**
     * key.
     */
    private int key;

    /**
     *
     * @param key key.
     * @param name name.
     */
    public BaseAction(int key, String name) {
        this.name = name;
        this.key = key;
    }

    /**
     *
     * @param name name.
     */
    public BaseAction(String name) {
        this.name = name;
        this.key = key;
    }

    /**
     *
     * @param key key.
     */
    public void setKey(int key) {
        this.key = key;
    }


    /**
     *
     * @return int.
     */
    @Override
    public int key() {
        return this.key;
    }

    /**
     *
     * @return info.
     */
    @Override
    public String info() {
        return String.format("%s. %s", this.key(), this.name);
    }

}
