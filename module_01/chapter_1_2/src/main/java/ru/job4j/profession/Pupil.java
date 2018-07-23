package ru.job4j.profession;

/**
 * Pupil.
 */
public class Pupil {
    /**
     * name.
     */
    private String name;

    /**
     *
     * @param name name.
     */
    public Pupil(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
