package ru.job4j.profession;

/**
 * Human.
 */
public class Human {
    /**
     * name.
     */
    private String name;

    /**
     *
     * @param name name.
     */
    public Human(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
