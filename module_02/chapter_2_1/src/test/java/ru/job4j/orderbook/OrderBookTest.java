package ru.job4j.orderbook;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OrderBookTest {

    @Test
    public void printOrderBook() throws Exception {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("Book-1", Operation.BUY, 10.1f, 10, 1));
        orders.add(new Order("Book-2", Operation.BUY, 10.3f, 10, 2));
        orders.add(new Order("Book-3", Operation.SELL, 10.1f, 15, 3));
        orders.add(new Order("Book-1", Operation.SELL, 10.2f, 10, 4));
        orders.add(new Order("Book-2", Operation.BUY, 9.4f, 50, 5));
        orders.add(new Order("Book-3", Operation.BUY, 8.4f, 150, 6));
        orders.add(new Order("Book-1", Operation.BUY, 7.4f, 250, 7));
        orders.add(new Order("Book-2", Operation.BUY, 6.4f, 350, 8));
        orders.add(new Order("Book-3", Operation.BUY, 6.4f, 450, 9));
        orders.add(new Order("Book-1", Operation.SELL, 16.04f, 150, 10));
        orders.add(new Order("Book-2", Operation.SELL, 16.14f, 250, 11));
        orders.add(new Order("Book-3", Operation.SELL, 16.24f, 350, 12));
        orders.add(new Order("Book-1", Operation.SELL, 16.34f, 450, 13));
        orders.add(new Order("Book-2", Operation.SELL, 16.34f, 450, 14));
        orders.add(new Order("Book-3", Operation.SELL, 16.44f, 550, 15));
        orders.add(new Order("Book-1", Operation.SELL, 16.54f, 650, 16));

        OrderBook orderBook = new OrderBook();

        for (Order order: orders) {
            orderBook.addOrder(order);
        }
        System.out.println(orderBook.printOrderBook());

    }

}