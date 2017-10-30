package ru.job4j.orderbook;

class Order {
    private final String book;
    private final Enum<Operation> operation;
    private final float price;
    private int volume;
    private final int orderId;

    String getBook() {
        return book;
    }

    Enum<Operation> getOperation() {
        return operation;
    }

    float getPrice() {
        return price;
    }

    int getVolume() {
        return volume;
    }

    int getOrderId() {
        return orderId;
    }

    Order(String book, Enum<Operation> operation, float price, int volume, int orderId) {
        this.book = book;
        this.operation = operation;
        this.price = price;
        this.volume = volume;
        this.orderId = orderId;
    }


    boolean cutVolume(int volume) {
        boolean result = false;
        if (this.volume > volume) {
            this.volume -= volume;
            result = true;
        }
        return result;
    }
}
