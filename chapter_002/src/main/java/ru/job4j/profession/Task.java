package ru.job4j.profession;

/**
 * Task.
 */
public class Task {
    /**
     * task.
     */
    private String task;

    /**
     *
     * @param task task.
     */
    public Task(String task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return this.task;
    }
}
