package ru.job4j.vendingmachine;

/**
 * .
 */
public class NotEnoughCoinsForExchangeException extends Throwable {
    /**
     *
     * @param msg .
     */
    public NotEnoughCoinsForExchangeException(String msg) {
        super(msg);
    }
}
