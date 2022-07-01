package org.example.xmlHandlers;

import lombok.extern.slf4j.Slf4j;
import org.example.pojos.Product;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import static org.example.utils.Utility.getFileId;

@Slf4j
public class XmlHandler {

  XmlParser xmlParser;
  XmlCreator xmlCreator;

  public XmlHandler(XmlParser xmlParser, XmlCreator xmlCreator) {
    this.xmlParser = xmlParser;
    this.xmlCreator = xmlCreator;
  }

  /**
   * @param fileName - filename received from the watch service
   * @throws FileNotFoundException
   */
  public void processFile(String fileName) throws FileNotFoundException {
    int fileId = getFileId(fileName);
    Map<String, List<Product>> suppliersProducts;
    suppliersProducts = xmlParser.parseOrder(fileName);
    xmlCreator.productToXML(suppliersProducts, fileId);
  }
}
