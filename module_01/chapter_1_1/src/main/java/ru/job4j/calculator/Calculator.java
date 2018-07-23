package ru.job4j.calculator;

/**
 * Class Calculator.
 */
public class Calculator {
    /**
     *
     */
    private double result;

    /**
     *
     * @param first first
     * @param second second
     */
    void add(double first, double second) {
        this.result = first + second;
    }

    /**
     *
     * @param first first
     * @param second second
     */
    void subtract(double first, double second) {
        this.result = first - second;
    }

    /**
     *
     * @param first first
     * @param second second
     */
    void div(double first, double second) {
        this.result = first / second;
    }

    /**
     *
     * @param first first
     * @param second second
     */
    void multiple(double first, double second) {
        this.result = first * second;
    }

    /**
     *
     * @return result
     */
    public double getResult() {
        return this.result;
    }
}
