package ru.job4j.jdbc;


import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import org.w3c.dom.Document;


/**
 * XML XSLT JDBC Оптимизация [#20459].
 * Создание и заполнение таблицы в бд - JDBC.
 * Формирование по данным таблицы XML-файла - JAXB.
 * Преобразование исходного XML-файла посредством XSLT - Transformer.
 * Чтение нового XML-файла и подсчет суммы атрибутов - JAXB.
 *
 */
public class SQLtoXML {
    private String url;
    private String username;
    private String password;
    private int atrCount;
    private EntriesIn entriesIn;
    private EntriesOut entriesOut;
    private File inputXML = new File("1.xml");
    private File outputXML = new File("2.xml");
    private File fileXSL = new File("2.xsl");

    public SQLtoXML() {
    }

    private SQLtoXML(String url, String username, String password, int atrCount) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.atrCount = atrCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAtrCount() {
        return atrCount;
    }

    public void setAtrCount(int atrCount) {
        this.atrCount = atrCount;
    }

    private void start() {
        System.out.println(String.format("Старт программы для %,d элементов", atrCount));
        Long start = System.currentTimeMillis();

//        setTable();
        setTableCycle();
        Long time = System.currentTimeMillis() - start;
        System.out.println(String.format("Создание таблицы в базе данных за %,d сек", time / 1000));
        start = System.currentTimeMillis();

        setXML();
        Long time2 = System.currentTimeMillis() - start;
        time += time2;
        System.out.println(String.format("Чтение таблицы из базы, запись исходного XML за %,d сек, общее время работы %,d сек", time2 / 1000, time / 1000));
        start = System.currentTimeMillis();

//        transformXMLDOM();
        transformXMLStream();
        time2 = System.currentTimeMillis() - start;
        time += time2;
        System.out.println(String.format("Трансформация XML за %,d сек, общее время работы %,d сек", time2 / 1000, time / 1000));
        start = System.currentTimeMillis();

        readXML();
        time2 = System.currentTimeMillis() - start;
        time += time2;
        System.out.println(String.format("Чтение итогового XML и расчет за %,d сек, общее время работы %,d сек", time2 / 1000, time / 1000));
    }

    private void readXML() {
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(EntriesOut.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            entriesOut = (EntriesOut) jaxbUnmarshaller.unmarshal(outputXML);

            long sum = 0L;
            for (EntryOut ent: entriesOut.entry) {
                sum += ent.field;
            }
            System.out.println(String.format("Сумма значений атрибутов: %,d", sum));

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    private void transformXMLStream() {

        try {
            StreamSource styleSource = new StreamSource(new FileInputStream(fileXSL));
            Transformer transformer = TransformerFactory.newInstance().newTransformer(styleSource);

            StreamSource source = new StreamSource(new FileInputStream(inputXML));

            StreamResult result = new StreamResult(new FileOutputStream(outputXML));

            transformer.transform(source, result);

        } catch (TransformerException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void transformXMLDOM() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            StreamSource styleSource = new StreamSource(fileXSL);
            Transformer transformer = TransformerFactory.newInstance().newTransformer(styleSource);

            Document document = factory.newDocumentBuilder().parse(inputXML);
            DOMSource source = new DOMSource(document);

            StreamResult result = new StreamResult(outputXML);

            transformer.transform(source, result);

        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private void setXML() {
        entriesIn = new EntriesIn();
        entriesIn.setEntry(new LinkedList<>());

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM test")) {
            while (rs.next()) {
                EntryIn entry = new EntryIn();
                entry.setField(rs.getInt("field"));
                entriesIn.getEntry().add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(EntriesIn.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(entriesIn, inputXML);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }


    private void setTableCycle() {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            conn.setAutoCommit(false);
            try {
                try (Statement st = conn.createStatement()) {
                    st.executeUpdate("CREATE TABLE IF NOT EXISTS test (field INT)");
                }
                try (Statement st = conn.createStatement()) {
                    st.executeUpdate("DELETE FROM test");
                }

                int numberWriteCycle = 0;
                int writeCycle = 1_000;
                int value = 0;
                while (value < atrCount) {
                    value = numberWriteCycle * writeCycle;
                    StringBuilder sb = new StringBuilder();
                    sb.append("INSERT INTO test (field) VALUES ");
                    for (int i = 0; i < writeCycle && value < atrCount; i++) {
                        sb.append(String.format("(%s),", ++value));
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    try (Statement st = conn.createStatement()) {
                        st.executeUpdate(sb.toString());
                    }
                    numberWriteCycle++;
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setTable() {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            conn.setAutoCommit(false);
            try {
                try (Statement st = conn.createStatement()) {
                    st.executeUpdate("CREATE TABLE IF NOT EXISTS test (field INT)");
                }
                try (Statement st = conn.createStatement()) {
                    st.executeUpdate("DELETE FROM test");
                }
                for (int i = 0; i < atrCount; i++) {
                    try (PreparedStatement st = conn.prepareStatement("INSERT INTO test (field) VALUES (?)")) {
                        st.setLong(1, i);
                        st.executeUpdate();
                    }
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        SQLtoXML app = new SQLtoXML(
                "jdbc:postgresql://localhost:5432/sqlite",
                "postgres",
                "password",
                1_000_000
        );
        app.start();
    }
}

@XmlRootElement(name = "entries")
class EntriesIn {

    private List<EntryIn> entry;

    List<EntryIn> getEntry() {
        return entry;
    }

    @XmlElement
    void setEntry(List<EntryIn> entry) {
        this.entry = entry;
    }
}

class EntryIn {
    private int field;

    public int getField() {
        return field;
    }

    @XmlElement
    void setField(int field) {
        this.field = field;
    }

}

@XmlRootElement(name = "entries")
class EntriesOut {

    List<EntryOut> entry;

    public List<EntryOut> getEntry() {
        return entry;
    }

    @XmlElement
    public void setEntry(List<EntryOut> entry) {
        this.entry = entry;
    }
}


class EntryOut {
    int field;

    public int getField() {
        return field;
    }

    @XmlAttribute
    public void setField(int field) {
        this.field = field;
    }
}
