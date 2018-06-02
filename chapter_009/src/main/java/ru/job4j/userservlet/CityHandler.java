package ru.job4j.userservlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class CityHandler {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.userservlet.CityHandler.class);


    public Set<String> getCountries() {
        LOGGER.traceEntry();
        Set<String> result = new TreeSet<>();
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputStream inputStream = CityHandler.class.getClassLoader().getResourceAsStream("cities.xml");
            Document document = documentBuilder.parse(inputStream);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("country");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                result.add(element.getElementsByTagName("countryname").item(0).getFirstChild().getNodeValue());
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOGGER.error("error", e);
        }
        return result;
    }

    public Set<String> getCities(String country) {
        LOGGER.traceEntry();
        Set<String> result = new TreeSet<>();
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputStream inputStream = CityHandler.class.getClassLoader().getResourceAsStream("cities.xml");
            Document document = documentBuilder.parse(inputStream);
            document.getDocumentElement().normalize();
            NodeList countryList = document.getElementsByTagName("country");
            for (int i = 0; i < countryList.getLength(); i++) {
                Element element = (Element) countryList.item(i);
                if (element.getElementsByTagName("countryname").item(0).getFirstChild().getNodeValue().equals(country)) {
                    NodeList cityList = ((Element) element.getElementsByTagName("citylist").item(0)).getElementsByTagName("city");
                    for (int j = 0; j < cityList.getLength(); j++) {
                        Element city = (Element) cityList.item(j);
                        result.add(city.getFirstChild().getNodeValue());
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOGGER.error("error", e);
        }
        return result;
    }
}
