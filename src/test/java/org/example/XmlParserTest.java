package org.example;
import org.example.pojos.Product;
import org.example.xmlHandlers.XmlParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class XmlParserTest {

    DocumentBuilderFactory stubDocumentBuilderFactory;
    XmlParser mockXmlParser;

    @BeforeEach
    public void setup() {
        stubDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        mockXmlParser = new XmlParser(stubDocumentBuilderFactory);
    }

    @Test
    public void parseOrder_ThrowsFileNotFoundException_ifFileNotFound() {
        assertThrows(FileNotFoundException.class, () ->  mockXmlParser.parseOrder("aRandomFileName.rand"));
    }

    @Test
    public void parseOrder_ReturnsGoodSuppliers_validFile() throws FileNotFoundException {

        Map<String, List<Product>> stringListMap = mockXmlParser.parseOrder("orders23.xml");
        Map<String, List<Product>> expected = new HashMap<>();
        expected.put("Sony", null);
        expected.put("Apple", null);
        expected.put("Panasonic", null);

        assertEquals(expected.keySet(), stringListMap.keySet());
    }



}
