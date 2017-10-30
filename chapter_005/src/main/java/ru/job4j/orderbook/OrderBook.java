package ru.job4j.orderbook;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;
import java.util.Map;

public class OrderBook {
    private Map<String, Book> books = new HashMap<>();

    void addOrder(Order order) {
        if (books.get(order.getBook()) == null) {
            books.put(order.getBook(), new Book(order.getBook()));
        }
        if (order.getVolume() > 0 && order.getPrice() > 0) {
            books.get(order.getBook()).addOrder(order);
        }
    }

    boolean deleteOrder(int orderId) {
        boolean result = false;
        for (Map.Entry<String, Book> entry : books.entrySet()) {
            if (entry.getValue().deleteOrder(orderId)) {
                result = true;
                break;
            }
        }
        return result;
    }

    String printOrderBook() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Book> entry : books.entrySet()) {
            result.append(entry.getValue());
        }
        return result.toString();
    }

}
