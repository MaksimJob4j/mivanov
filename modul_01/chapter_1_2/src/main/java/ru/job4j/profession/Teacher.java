package ru.job4j.profession;

/**
 * Teacher.
 */
public class Teacher extends Profession {
    /**
     *
     * @param pupil pupil.
     * @return String.
     */
    public String teach(Pupil pupil) {
        return "Учитель" + this.getName() + " учит " + pupil;
    }
}
