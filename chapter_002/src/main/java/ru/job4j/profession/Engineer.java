package ru.job4j.profession;

/**
 * Engineer.
 */
public class Engineer extends Profession {
    /**
     *
     * @param task Task.
     * @return String.
     */
    public String develop(Task task) {
        return "Инженер " + this.getName() + " рабатывает " + task;
    }
}
