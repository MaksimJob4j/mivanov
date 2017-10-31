package ru.job4j.orderbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OrderBook {
    private Map<String, Book> books = new HashMap<>();

    boolean addOrder(Order order) {
        boolean result = false;
        if (books.get(order.getBook()) == null) {
            books.put(order.getBook(), new Book(order.getBook()));
        }
        if (order.getVolume() > 0 && order.getPrice() > 0) {
            result = books.get(order.getBook()).addOrder(order);
        }
        return result;
    }

    boolean deleteOrder(String book, int orderId) {
        return books.get(book).deleteOrder(orderId);
    }

    /**
     * Строковое представление текущего состояния OrderBook.
     * @return String.
     */
    String printOrderBook() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Book> entry : books.entrySet()) {
            result.append(entry.getValue());
        }
        return result.toString();
    }

    /**
     * Метод запускает программу (читат файли добавляет команды в OrderBook).
     * @return количество совершенных операций
     * @throws IOException
     */
    public int start() throws IOException {
        int result = 0;
        File file = new File("orders.xml");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.contains("AddOrder")) {

                int startIndex = line.indexOf("\"", line.indexOf("book=")) + 1;
                int endIndex = line.indexOf("\"", startIndex);
                String book = line.substring(startIndex, endIndex);

                startIndex = line.indexOf("\"", line.indexOf("operation=")) + 1;
                endIndex = line.indexOf("\"", startIndex);
                Operation operation = line.substring(startIndex, endIndex).equalsIgnoreCase("SELL")
                        ? Operation.SELL : line.substring(startIndex, endIndex).equalsIgnoreCase("BUY")
                        ? Operation.BUY : null;


                startIndex = line.indexOf("\"", line.indexOf("price=")) + 1;
                endIndex = line.indexOf("\"", startIndex);
                float price = Float.parseFloat(line.substring(startIndex, endIndex));

                startIndex = line.indexOf("\"", line.indexOf("volume=")) + 1;
                endIndex = line.indexOf("\"", startIndex);
                int volume = Integer.parseInt(line.substring(startIndex, endIndex));

                startIndex = line.indexOf("\"", line.indexOf("orderId=")) + 1;
                endIndex = line.indexOf("\"", startIndex);
                int orderId = Integer.parseInt(line.substring(startIndex, endIndex));

                if (addOrder(new Order(book, operation, price, volume, orderId))) {
                    result++;
                }

            } else if (line.contains("DeleteOrder")) {

                int startIndex = line.indexOf("\"", line.indexOf("book=")) + 1;
                int endIndex = line.indexOf("\"", startIndex);
                String book = line.substring(startIndex, endIndex);

                startIndex = line.indexOf("\"", line.indexOf("orderId=")) + 1;
                endIndex = line.indexOf("\"", startIndex);
                int orderId = Integer.parseInt(line.substring(startIndex, endIndex));

                if (deleteOrder(book, orderId)) {
                    result++;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        OrderBook orderBook = new OrderBook();
        try {
            orderBook.start();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        System.out.println(orderBook.printOrderBook());
    }
}
