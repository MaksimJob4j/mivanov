package ru.job4j.profession;

/**
 * University.
 */
public class University {
    /**
     * name.
     */
    private String name;

    /**
     *
     * @param name name.
     */
    public University(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
