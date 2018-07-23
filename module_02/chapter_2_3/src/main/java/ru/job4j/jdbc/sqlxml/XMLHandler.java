package ru.job4j.jdbc.sqlxml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

class XMLHandler {
    static void writeXML(EntriesIn entriesIn, File writeFile) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(EntriesIn.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(entriesIn, writeFile);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    static EntriesOut readXML(File readFile) {
        EntriesOut entriesOut = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(EntriesOut.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            entriesOut = (EntriesOut) jaxbUnmarshaller.unmarshal(readFile);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return entriesOut;
    }
}
