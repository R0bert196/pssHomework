package org.example.xmlHandlers;

import org.example.pojos.Product;

import java.util.List;
import java.util.Map;

public class XmlHandler {

    XmlParser xmlParser;
    XmlCreator xmlCreator;

    public XmlHandler(XmlParser xmlParser, XmlCreator xmlCreator) {
        this.xmlParser = xmlParser;
        this.xmlCreator = xmlCreator;
    }

    public void processFile(String fileName)  {
        int fileId = validateFilename(fileName);
        Map<String, List<Product>> suppliersProducts;
        try {
            suppliersProducts = xmlParser.parseOrderXml(fileName);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        xmlCreator.productToXML(suppliersProducts,  fileId);

    }

    private int validateFilename(String fileName) {
        try {
            return Integer.parseInt(fileName.substring(6, 8));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Incorrect file name, file isn't of type orders##.xml");
        }
    }
}
