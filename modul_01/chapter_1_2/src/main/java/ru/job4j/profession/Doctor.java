package ru.job4j.profession;

/**
 * Doctor.
 */
public class Doctor extends Profession {
    /**
     *
     * @param pacient Human.
     * @return String.
     */
    public String heal(Human pacient) {
        return "Доктор " + this.getName() + " лечит " + pacient;
    }
}
