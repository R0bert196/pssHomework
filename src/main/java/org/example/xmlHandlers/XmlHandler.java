package org.example.xmlHandlers;

import org.example.pojos.Product;

import java.util.List;
import java.util.Map;

import static org.example.utils.Utility.validateFilename;

public class XmlHandler {

  XmlParser xmlParser;
  XmlCreator xmlCreator;

  public XmlHandler(XmlParser xmlParser, XmlCreator xmlCreator) {
    this.xmlParser = xmlParser;
    this.xmlCreator = xmlCreator;
  }

  public void processFile(String fileName) {
    int fileId = validateFilename(fileName);
    Map<String, List<Product>> suppliersProducts;
    try {
      suppliersProducts = xmlParser.parseOrder(fileName);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    xmlCreator.productToXML(suppliersProducts, fileId);
  }
}
