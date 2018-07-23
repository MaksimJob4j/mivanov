package ru.job4j.jdbc.sqlxml;

import java.io.File;

/**
 * XML XSLT JDBC Оптимизация [#20459].
 * Создание и заполнение таблицы в бд - JDBC.
 * Формирование по данным таблицы XML-файла - JAXB.
 * Преобразование исходного XML-файла посредством XSLT - Transformer.
 * Чтение нового XML-файла и подсчет суммы атрибутов - JAXB.
 *
 */
public class SQLtoXML {

    Long start(int atrNumb, String url, String username, String password, File firstXML, File secondXML, File fileXSL) {

        System.out.println(String.format("Старт программы для %,d элементов", atrNumb));
        Long start = System.currentTimeMillis();

        SQLHandler handler = new SQLHandler(url, username, password);
        handler.setTable(atrNumb);

        Long time = System.currentTimeMillis() - start;
        System.out.println(String.format("Создание таблицы в базе данных за %,d сек", time / 1000));
        start = System.currentTimeMillis();

        EntriesIn entriesIn = handler.readFromBaseToClass();
        XMLHandler.writeXML(entriesIn, firstXML);

        Long time2 = System.currentTimeMillis() - start;
        time += time2;
        System.out.println(String.format("Чтение таблицы из базы, запись исходного XML за %,d сек, общее время работы %,d сек", time2 / 1000, time / 1000));
        start = System.currentTimeMillis();


        time2 = System.currentTimeMillis() - start;
        time += time2;
        System.out.println(String.format("Трансформация XML за %,d сек, общее время работы %,d сек", time2 / 1000, time / 1000));
        start = System.currentTimeMillis();

        XSLTransformer.transformXML(firstXML, secondXML, fileXSL);
        EntriesOut entriesOut = XMLHandler.readXML(secondXML);
        Long result = entriesOut.sumAttributes();

        time2 = System.currentTimeMillis() - start;
        time += time2;
        System.out.println(String.format("Чтение итогового XML и расчет за %,d сек, общее время работы %,d сек", time2 / 1000, time / 1000));

        return result;
    }

    public static void main(String[] args) {
        int atrNumb = 1_000_000;
        SQLtoXML sqLtoXML = new SQLtoXML();
        Long attrSum = sqLtoXML.start(
                atrNumb,
                "jdbc:postgresql://localhost:5432/sqlite",
                "postgres",
                "password",
                new File("1.xml"),
                new File("2.xml"),
                new File("chapter_008/src/main/resources/2.xsl")
        );
        System.out.println(String.format("Сумма %,d аттрибутов: %,d", atrNumb, attrSum));
    }
}


