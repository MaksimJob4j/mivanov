package ru.job4j.orderbook;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderBook {

    private int countAdd = 0;
    private int countNotAdd = 0;
    private int countDell = 0;
    private int countNotDell = 0;

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
     * Метод запускает программу (читат файл и добавляет команды в OrderBook).
     * Разбор файла проводится через String за 1 проход строки.
     * @param file файл заявок.
     * @return стакан в формате String.
     * @throws IOException IOException.
     */
    public String startStringNew(File file) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        while ((line = reader.readLine()) != null) {

            if (line.charAt(1) == 'A') {
                ArrayList<String> values = parseLine(line);
                addOrder(new Order(
                        values.get(0),
                        values.get(1).equalsIgnoreCase("SELL") ? Operation.SELL : Operation.BUY,
                        Float.valueOf(values.get(2)),
                        Integer.valueOf(values.get(3)),
                        Integer.valueOf(values.get(4))
                ));
            } else if (line.charAt(1) == 'D') {
                ArrayList<String> values = parseLine(line);
                deleteOrder(
                        values.get(0),
                        Integer.valueOf(values.get(1))
                );
            }
        }
        return printOrderBook();
    }

    private ArrayList<String> parseLine(String line) {
        ArrayList<String> values = new ArrayList<>();
        Boolean start = false;
        int beginIndex = 0;
        for (int i = 0; i < line.length(); i++) {

            if (line.charAt(i) == '\"') {
                if (start) {
                    values.add(line.substring(beginIndex, i));
                    start = false;
                } else {
                    start = true;
                    beginIndex = i + 1;
                }
            }
        }
        return values;
    }

    /**
     * Метод запускает программу (читат файли добавляет команды в OrderBook).
     * Разбор файла проводится через String.
     * @param file файл заявок.
     * @return стакан в формате String.
     * @throws IOException IOException.
     */
    public String startString(File file) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
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
                    countAdd++;
                } else {
                    countNotAdd++;
                }

            } else if (line.contains("DeleteOrder")) {

                int startIndex = line.indexOf("\"", line.indexOf("book=")) + 1;
                int endIndex = line.indexOf("\"", startIndex);
                String book = line.substring(startIndex, endIndex);

                startIndex = line.indexOf("\"", line.indexOf("orderId=")) + 1;
                endIndex = line.indexOf("\"", startIndex);
                int orderId = Integer.parseInt(line.substring(startIndex, endIndex));

                if (deleteOrder(book, orderId)) {
                    countDell++;
                } else {
                    countNotDell++;
                }
            }
        }
        return printOrderBook();
    }

    /**
     * Метод запускает программу (читат файли добавляет команды в OrderBook).
     * Разбор XML файла проводится с использованием Java StAX API.
     * @param file файл заявок.
     * @return стакан в формате String.
     * @throws IOException IOException.
     */
    public String startXML(File file) throws IOException, XMLStreamException {

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        // инициализируем reader и скармливаем ему xml файл
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(file));

        // проходим по всем элементам xml файла
        while (reader.hasNext()) {
            // получаем событие (элемент) и разбираем его по атрибутам
            XMLEvent xmlEvent = reader.nextEvent();
            if (xmlEvent.isStartElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                if (startElement.getName().getLocalPart().equals("AddOrder")) {

                    // Получаем атрибут для каждого элемента
                    Attribute bookAttr = startElement.getAttributeByName(new QName("book"));
                    String book = bookAttr.getValue();

                    Attribute operationAttr = startElement.getAttributeByName(new QName("operation"));
                    Operation operation = operationAttr.getValue().equalsIgnoreCase("SELL")
                            ? Operation.SELL : operationAttr.getValue().equalsIgnoreCase("BUY")
                            ? Operation.BUY : null;

                    Attribute priceAttr = startElement.getAttributeByName(new QName("price"));
                    float price = Float.parseFloat(priceAttr.getValue());

                    Attribute volumeAttr = startElement.getAttributeByName(new QName("volume"));
                    int volume = Integer.parseInt(volumeAttr.getValue());

                    Attribute orderIdAttr = startElement.getAttributeByName(new QName("orderId"));
                    int orderId = Integer.parseInt(orderIdAttr.getValue());

                    if (addOrder(new Order(book, operation, price, volume, orderId))) {
                        countAdd++;
                    } else {
                        countNotAdd++;
                    }

                } else if (startElement.getName().getLocalPart().equals("DeleteOrder")) {

                    // Получаем атрибут для каждого элемента
                    Attribute bookAttr = startElement.getAttributeByName(new QName("book"));
                    String book = bookAttr.getValue();

                    Attribute orderIdAttr = startElement.getAttributeByName(new QName("orderId"));
                    int orderId = Integer.parseInt(orderIdAttr.getValue());

                    if (deleteOrder(book, orderId)) {
                        countDell++;
                    } else {
                        countNotDell++;
                    }
                }
            }
        }
        return printOrderBook();
    }


    public static void main(String[] args) {
        File file = new File("orders.xml");

        // Подсчет времени работы вырианта
        // с парсингом через XMLInputFactory и получение атрибутов каждого элемента
        Long timeXML = -System.currentTimeMillis();

        OrderBook oBXML = new OrderBook();
        String orderBookXML = "";

        try {
            orderBookXML = oBXML.startXML(file);
        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
        }
        timeXML += System.currentTimeMillis();

        // Подсчет времени работы вырианта
        // с чтением через BufferedReader и парсингом методами String
        Long timeString = -System.currentTimeMillis();
        OrderBook oBString = new OrderBook();
        String orderBookString = "";
        try {
            orderBookString = oBString.startString(file);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        timeString += System.currentTimeMillis();

        // Подсчет времени работы вырианта
        // с чтением через BufferedReader и парсингом методами String за 1 проход строки
        Long timeStringNew = -System.currentTimeMillis();
        OrderBook oBStringNew = new OrderBook();
        String orderBookStringNew = "";
        try {
            orderBookStringNew = oBStringNew.startString(file);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        timeStringNew += System.currentTimeMillis();

        if (orderBookXML.equals(orderBookStringNew)) {
            System.out.printf("Результат методов StAX API и String идентичен");
            System.out.println();
            System.out.println(orderBookString);
        } else {
            System.out.println("Результаты методов StAX API и String не совпадают");
            System.out.println("Результаты метода StAX API:");
            System.out.println(orderBookXML);
            System.out.println("Результаты метода String:");
            System.out.println(orderBookStringNew);
        }

        System.out.println();
        System.out.printf("Время выполнение StAX API - %S Мсек, добавлено %S, удалено/отклонено %S/%S",
                timeXML, oBXML.countAdd, oBXML.countDell, oBXML.countNotDell);
        System.out.println();
        System.out.printf("Время выполнение String - %S Мсек, добавлено %S, удалено/отклонено %S/%S",
                timeString, oBString.countAdd, oBString.countDell, oBString.countNotDell);
        System.out.println();
        System.out.printf("Время выполнение StringNEW - %S Мсек, добавлено %S, удалено/отклонено %S/%S",
                timeStringNew, oBStringNew.countAdd, oBStringNew.countDell, oBStringNew.countNotDell);

    }
}
