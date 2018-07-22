package ru.job4j.vendingmachine;

/**
 * Номиналы монет загружаемые в автомат.
 */
public enum Nominal {
    /**
     * .
     */
    TEN(10),
    /**
     * .
     */
    FIVE(5),
    /**
     * .
     */
    TWO(2),
    /**
     * .
     */
    ONE(1);

    /**
     * номинал монеты.
     */
    private final int value;

    /**
     *
     * @param value .
     */
    Nominal(int value) {
        this.value = value;
    }

    /**
     *
     * @return .
     */
    public int getValue() {
        return value;
    }


}
