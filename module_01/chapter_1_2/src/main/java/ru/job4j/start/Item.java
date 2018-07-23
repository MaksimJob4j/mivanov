package ru.job4j.start;

import java.util.ArrayList;

/**
 * Заявка.
 */
public class Item {
    /**
     * id.
     */
    private Integer id = null;
    /**
     * name.
     */
    private String name;
    /**
     * task.
     */
    private String task;
    /**
     * created.
     */
    private long created;
    /**
     * comments.
     */
    private ArrayList<String> comments;

    /**
     *
     * @param name name.
     * @param task task.
     * @param created created.
     */
    Item(String name, String task, long created) {
        this.name = name;
        this.task = task;
        this.created = created;
    }

    /**
     *
     * @return id.
     */
    Integer getId() {
        return id;
    }

    void setID(Integer id) {
        if (id != null && this.id == null) {
            this.id = id;
        }
    }

    /**
     *
     * @return name.
     */
    String getName() {
        return name;
    }

    /**
     *
     * @param name name.
     */
    void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return task.
     */
    String getTask() {
        return task;
    }

    /**
     *
     * @param task task.
     */
    void setTask(String task) {
        this.task = task;
    }

    /**
     *
     * @return created.
     */
    long getCreated() {
        return created;
    }

    /**
     *
     * @param created created.
     */
    void setCreated(long created) {
        this.created = created;
    }

    /**
     *
     * @return comments.
     */
    ArrayList<String> getComments() {
        return comments;
    }

    /**
     *
     * @param comments comments.
     */
    void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    /**
     *
     * @return toString.
     */
    public String toString() {
        return "ID: " + this.id + System.getProperty("line.separator")
                + "name: " + this.name + System.getProperty("line.separator")
                + "task: " + this.task + System.getProperty("line.separator")
                + "created: " + this.created + System.getProperty("line.separator")
                + "comments: " + this.comments + System.getProperty("line.separator");
    }

    boolean equalsWithoutId(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Item item = (Item) o;

        if (item.name != null && item.name.equals(this.name) || item.name == null && this.name == null) {
            if (item.task != null && item.task.equals(this.task) || item.task == null && this.task == null) {
                return item.created == this.created;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Item item = (Item) o;

        return id.equals(item.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

