package ru.job4j.jdbc.sqlxml;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

class XSLTransformer {
    static void transformXML(File inputXML, File outputXML, File fileXSL) {

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
}
