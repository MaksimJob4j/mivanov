package ru.job4j.vendingmachine;

/**
 * .
 */
public class NotEnoughMoneyException extends Throwable {
    /**
     *
     * @param msg .
     */
    public NotEnoughMoneyException(String msg) {
        super(msg);
    }
}
