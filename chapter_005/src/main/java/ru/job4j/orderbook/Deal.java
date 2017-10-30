package ru.job4j.orderbook;

/**
 * Сделки.
 */
class Deal {
    private static int dealCounter = 0;
    private final String book;
    private final Float price;
    private final int volume;
    private final int orderIdBuyer;
    private final int orderIdSeller;
    private final int dealId;

    Deal(String book, Float price, int volume, int orderIdBuyer, int orderIdSeller) {
        this.book = book;
        this.price = price;
        this.volume = volume;
        this.orderIdBuyer = orderIdBuyer;
        this.orderIdSeller = orderIdSeller;
        dealId = dealCounter++;
    }
}
