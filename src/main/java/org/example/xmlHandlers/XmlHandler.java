package org.example.xmlHandlers;

import lombok.extern.slf4j.Slf4j;
import org.example.pojos.Product;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import static org.example.utils.Utility.validateFilename;

@Slf4j
public class XmlHandler {

  XmlParser xmlParser;
  XmlCreator xmlCreator;

  public XmlHandler(XmlParser xmlParser, XmlCreator xmlCreator) {
    this.xmlParser = xmlParser;
    this.xmlCreator = xmlCreator;
  }

  public void processFile(String fileName) throws FileNotFoundException {
    int fileId = validateFilename(fileName);
    Map<String, List<Product>> suppliersProducts;
    suppliersProducts = xmlParser.parseOrder(fileName);
    xmlCreator.productToXML(suppliersProducts, fileId);
  }
}
