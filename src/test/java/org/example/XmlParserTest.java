package org.example;
import org.example.xmlHandlers.XmlParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class XmlParserTest {

    DocumentBuilderFactory stubDocumentBuilderFactory;
    XmlParser mockXmlParser;

    @BeforeEach
    public void setup() {
        stubDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        mockXmlParser = new XmlParser(stubDocumentBuilderFactory);
    }

    @Test
    public void parseOrder_ThrowsFileNotFoundException_ifFileNotFound() throws FileNotFoundException {
    assertThrows(FileNotFoundException.class, () ->  mockXmlParser.parseOrder("aRandomFileName.rand"));
    }


}
