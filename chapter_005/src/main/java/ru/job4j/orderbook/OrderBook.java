package ru.job4j.orderbook;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class OrderBook {

    int countOperationTotal = 0;
    int countOperation = 0;
    int countAdd = 0;
    int countNotAdd = 0;
    int countDell = 0;
    int countNotDell = 0;



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
     * Разбор файла проводится через String.
     * @return количество совершенных операций
     * @throws IOException
     */
    public int startString() throws IOException {
        File file = new File("orders.xml");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        int countOperationTotal = 0;
        while ((line = bufferedReader.readLine()) != null) {

            if (line.contains("AddOrder")) {
                countOperationTotal++;

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
                    countOperation++;
                    countAdd++;
                } else {
                    countNotAdd++;
                }

            } else if (line.contains("DeleteOrder")) {
                countOperationTotal++;

                int startIndex = line.indexOf("\"", line.indexOf("book=")) + 1;
                int endIndex = line.indexOf("\"", startIndex);
                String book = line.substring(startIndex, endIndex);

                startIndex = line.indexOf("\"", line.indexOf("orderId=")) + 1;
                endIndex = line.indexOf("\"", startIndex);
                int orderId = Integer.parseInt(line.substring(startIndex, endIndex));

                if (deleteOrder(book, orderId)) {
                    countOperation++;
                    countDell++;
                } else {
                    countNotDell++;
                }
            }
        }
        this.countOperationTotal = countOperationTotal;
        return countOperationTotal;
    }

    /**
     * Метод запускает программу (читат файли добавляет команды в OrderBook).
     * Разбор XML файла проводится с использованием Java StAX API.
     * @return количество совершенных операций
     * @throws IOException
     */
    public int startXML() throws IOException, XMLStreamException {
        int countOperationTotal = 0;

        File file = new File("orders.xml");

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        // инициализируем reader и скармливаем ему xml файл
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(new File("orders.xml")));

        // проходим по всем элементам xml файла
        while (reader.hasNext()) {
            // получаем событие (элемент) и разбираем его по атрибутам
            XMLEvent xmlEvent = reader.nextEvent();
            if (xmlEvent.isStartElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                if (startElement.getName().getLocalPart().equals("AddOrder")) {
                    countOperationTotal++;

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
                        countOperation++;
                        countAdd++;
                    } else {
                        countNotAdd++;
                    }

                } else if (startElement.getName().getLocalPart().equals("DeleteOrder")) {
                    countOperationTotal++;

                    // Получаем атрибут для каждого элемента
                    Attribute bookAttr = startElement.getAttributeByName(new QName("book"));
                    String book = bookAttr.getValue();

                    Attribute orderIdAttr = startElement.getAttributeByName(new QName("orderId"));
                    int orderId = Integer.parseInt(orderIdAttr.getValue());

                    if (deleteOrder(book, orderId)) {
                        countOperation++;
                        countDell++;
                    } else {
                        countNotDell++;
                    }
                }
            }
        }
        this.countOperationTotal = countOperationTotal;
        return countOperationTotal;
    }


    public static void main(String[] args) {

        Long timeXML = -System.currentTimeMillis();

        OrderBook oBXML = new OrderBook();
        int bookOperationXML = 0;

        try {
            bookOperationXML = oBXML.startXML();
        } catch (IOException ioe) {
            System.out.println(ioe);
        } catch (XMLStreamException e) {
            System.out.println(e);
        }
        String bookXML = oBXML.printOrderBook();
        timeXML += System.currentTimeMillis();

        Long timeString = -System.currentTimeMillis();
        OrderBook oBString = new OrderBook();
        int bookOperationString = 0;
        try {
            bookOperationString = oBString.startString();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        String bookString = oBXML.printOrderBook();
        timeString += System.currentTimeMillis();

        System.out.println();
        System.out.printf("Время выполнение StAX API - %S сек, к-во операций %S, добавлено/отклонено %S/%S, удалено/отклонено %S/%S",
                (timeXML / 1000), bookOperationXML, oBXML.countAdd, oBXML.countNotAdd, oBXML.countDell, oBXML.countNotDell);
        System.out.println();
        System.out.printf("Время выполнение String - %S сек, к-во операций %S, добавлено/отклонено %S/%S, удалено/отклонено %S/%S",
                (timeString / 1000), bookOperationString, oBString.countAdd, oBString.countNotAdd, oBString.countDell, oBString.countNotDell);
        System.out.println();
        System.out.println();

        if (bookString.equals(bookXML)) {
            System.out.printf("Результат методов StAX API и String идентичен");
            System.out.println();
            System.out.println(bookString);
        } else {
            System.out.println("Результаты методов StAX API и String не совпадают");
            System.out.println("Результаты метода StAX API:");
            System.out.println(bookString);
            System.out.println("Результаты метода String:");
            System.out.println(bookXML);
        }
    }
}
