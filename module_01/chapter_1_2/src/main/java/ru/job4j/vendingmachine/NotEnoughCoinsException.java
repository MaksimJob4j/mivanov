package ru.job4j.vendingmachine;

/**
 * .
 */
public class NotEnoughCoinsException extends Throwable {
    /**
     *
     * @param msg .
     */
    public NotEnoughCoinsException(String msg) {
        super(msg);
    }
}
