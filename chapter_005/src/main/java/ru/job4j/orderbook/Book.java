package ru.job4j.orderbook;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Book {

    private String name;

    public String getName() {
        return name;
    }

    Book(String name) {
        this.name = name;
    }

    /**
     * Bid orders.
     */
    private TreeMap<Float, LinkedList<Order>> bid = new TreeMap<>(new Comparator<Float>() {
        @Override
        public int compare(Float o1, Float o2) {
            return -o1.compareTo(o2);
        }
    });

    public TreeMap<Float, LinkedList<Order>> getBid() {
        return bid;
    }

    /**
     * Ask orders.
     */
    private TreeMap<Float, LinkedList<Order>> ask = new TreeMap<>();

    public TreeMap<Float, LinkedList<Order>> getAsk() {
        return ask;
    }

    /**
     * Deals.
     */
    private List<Deal> deals = new ArrayList<>();

    public List<Deal> getDeals() {
        return deals;
    }

    boolean addOrder(Order order) {
        boolean result = false;

            if (order.getOperation().equals(Operation.BUY)) {
                bid.computeIfAbsent(order.getPrice(), orderList -> new LinkedList<>()).add(order);
                result = true;
            } else if (order.getOperation().equals(Operation.SELL)) {
                ask.computeIfAbsent(order.getPrice(), orderList -> new LinkedList<>()).add(order);
                result = true;
            }

            makeDeals();
        return result;
    }

    private void makeDeals() {

        while (bid.size() > 0 && ask.size() > 0 && bid.firstKey() >= ask.firstKey()) {

            Order buyOrder = bid.firstEntry().getValue().getFirst();
            Order sellOrder = ask.firstEntry().getValue().getFirst();
            float price = buyOrder.getOrderId() < sellOrder.getOrderId() ? buyOrder.getPrice() : sellOrder.getPrice();
            int volume = Math.min(buyOrder.getVolume(), sellOrder.getVolume());

            deals.add(new Deal(name, price, volume, buyOrder.getOrderId(), sellOrder.getOrderId()));

            cutBook(bid, volume);
            cutBook(ask, volume);

        }
    }

    private void cutBook(TreeMap<Float, LinkedList<Order>> bookSide, int volume) {

        if (!bookSide.firstEntry().getValue().getFirst().cutVolume(volume)) {

            bookSide.firstEntry().getValue().removeFirst();

            if (bookSide.firstEntry().getValue().isEmpty()) {
                bookSide.remove(bookSide.firstKey());
            }
        }

    }

    boolean deleteOrder(Order order) {
        boolean result = false;
        if (order != null) {
            if (order.getOperation().equals(Operation.BUY)) {
                result = deleteOrder(bid, order);
            } else {
                result = deleteOrder(ask, order);
            }
        }
        return result;
    }

    private boolean deleteOrder(TreeMap<Float, LinkedList<Order>> bookSide, Order order) {
        boolean result = false;
        int i = -1;
        while (!result && ++i < bookSide.get(order.getPrice()).size()) {

            if (bookSide.get(order.getPrice()).get(i).getOrderId() == order.getOrderId()) {
                bookSide.get(order.getPrice()).remove(i);
                if (bookSide.get(order.getPrice()).isEmpty()) {
                    bookSide.remove(order.getPrice());
                }
                result = true;
            }
        }
        return result;
    }

    boolean deleteOrder(int orderId) {
        return deleteOrder(find(orderId));
    }

    private Order find(int orderId) {
        Order result = find(orderId, bid);
        if (result == null) {
            result = find(orderId, ask);
        }
        return result;
    }

    private Order find(int orderId, Map<Float, LinkedList<Order>> bookSide) {
        Order result = null;

        loops:
        for (Map.Entry<Float, LinkedList<Order>> entry: bookSide.entrySet()) {
            for (Order order: entry.getValue()) {
                if (order.getOrderId() == orderId) {
                    result = order;
                    break loops;
                }
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("\nOrder book: %s\n", name));
        result.append("      BID\t\t\t\t\t  ASK\n");
        result.append("Volume @   Price\t–\tVolume @   Price\n");
        ArrayList<BookItem> bids = bookSideToList(bid);
        ArrayList<BookItem> asks = bookSideToList(ask);
        int depth = Math.max(bids.size(), asks.size());
        for (int i = 0; i < depth; i++) {
            if (i < bids.size()) {
                result.append(String.format("%5s, @ %7s", bids.get(i).volume, bids.get(i).price));
            } else {
                result.append("------ @ -------");
            }
            result.append("\t-\t");
            if (i < asks.size()) {
                result.append(String.format("%5s, @ %7s\n", asks.get(i).volume, asks.get(i).price));
            } else {
                result.append("------ @ -------\n");
            }
        }
        return result.toString();
    }

    private ArrayList<BookItem> bookSideToList(TreeMap<Float, LinkedList<Order>> bookSide) {

        ArrayList<BookItem> result = new ArrayList<>();

        for (Entry<Float, LinkedList<Order>> entry: bookSide.entrySet()) {

            BookItem bookItem = new BookItem(entry.getKey());
            for (Order order: entry.getValue()) {
                bookItem.volume += order.getVolume();
            }
            result.add(bookItem);
        }
        return result;
    }

    private class BookItem {
        float price;
        int volume;

        BookItem(Float price) {
            this.price = price;
        }
    }
}
