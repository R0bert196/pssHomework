package org.example.xmlHandlers;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XmlParser {
    private final DocumentBuilderFactory factory;

    public XmlParser(DocumentBuilderFactory factory) {
        this.factory = factory;
    }

    public void parseOrderXml() {
        File xmlFile = new File("students.xml");
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

    }

}
