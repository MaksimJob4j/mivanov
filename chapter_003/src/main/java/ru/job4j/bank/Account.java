package ru.job4j.bank;

/**
 * Счет.
 */
public class Account {

    /**
     * Реквизиты.
     */
    private String requisites;

    /**
     * Деньги на счёте.
     */
    private double value = 0d;

    /**
     *
     * @param requisites requisites.
     */
    public Account(String requisites) {
        this.requisites = requisites;
    }

    /**
     *
     * @param requisites requisites.
     * @param value value.
     */
    public Account(String requisites, double value) {
        this.requisites = requisites;
        addValue(value);
    }


    /**
     *
     * @return requisites.
     */
    public String getRequisites() {
        return requisites;
    }

    /**
     *
     * @return value.
     */
    public double getValue() {
        return value;
    }

    /**
     * Положить деньги на счет.
     * @param value value .
     * @return boolean.
     */
    boolean addValue(double value) {
        boolean result = false;
        if (value > 0) {
            this.value += value;
            result = true;
        }
        return result;
    }

    /**
     * Снять деньги со счета.
     * @param value value.
     * @return boolean.
     */
    boolean withdrawValue(double value) {
        boolean result = false;
        if (value > 0 && this.value >= value) {
            this.value -= value;
            result = true;
        }
        return result;
    }
}
