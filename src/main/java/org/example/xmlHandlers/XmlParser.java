package org.example.xmlHandlers;

import lombok.extern.slf4j.Slf4j;
import org.example.configs.Config;
;
import org.example.pojos.Price;
import org.example.pojos.Product;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.util.*;

@Slf4j
public class XmlParser {
  private final DocumentBuilderFactory factory;

  public XmlParser(DocumentBuilderFactory factory) {
    this.factory = factory;
  }

  /**
   * @param fileName - the name of the file to be parsed
   * @return A map containing suppliers as key and a list of products for that key
   * @throws FileNotFoundException
   */
  // sa il numesc xmlToObject?
  public Map<String, List<Product>> parseOrder(String fileName) throws FileNotFoundException {
    try {
      Map<String, String> configProperties = Config.getConfigProperties();
      String inputPath = configProperties.get("inputPath");

      File xmlFile = new File(inputPath + "/" + fileName);

      this.factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
      NodeList orderNodes = getOrderNodes(xmlFile);
      return getProducts(orderNodes);
    } catch (ParserConfigurationException | IOException | SAXException e) {
      throw new FileNotFoundException(e.getMessage());
    }
  }

  /**
   * @param xmlFile - the added file
   * @return - NodeList containing the orders nodes
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws IOException
   */
  private NodeList getOrderNodes(File xmlFile)
      throws ParserConfigurationException, SAXException, IOException {
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(xmlFile);
    doc.getDocumentElement().normalize();
    return doc.getElementsByTagName("order");
  }

  /**
   * @param orderNodes - the NodeList containing the orders nodes
   * @return - A map containing suppliers as key and a list of products for that key
   */
  private Map<String, List<Product>> getProducts(NodeList orderNodes) {
    Map<String, List<Product>> suppliersProducts = new HashMap<>();

    // orders nodes
    for (int i = 0; i < orderNodes.getLength(); i++) {
      Node orderNode = orderNodes.item(i);
      Instant created = null;
      long orderId = 0;
      NodeList productNodes = orderNode.getChildNodes();
      if (orderNode.getNodeType() == Node.ELEMENT_NODE) {
        Element orderElement = (Element) orderNode;
        created = Instant.parse(orderElement.getAttribute("created") + "Z");
        orderId = Long.parseLong(orderElement.getAttribute("ID"));
      }

      // products nodes
      for (int j = 0; j < productNodes.getLength(); j++) {
        Node productNode = productNodes.item(j);
        if (productNode.getNodeType() == Node.ELEMENT_NODE) {
          Element element = (Element) productNode;
          NodeList priceNode = element.getElementsByTagName("price");
          Element priceElement = (Element) priceNode.item(0);
          String currency = priceElement.getAttribute("currency");

          Price price =
              Price.builder()
                  .price(
                      Float.parseFloat(
                          element.getElementsByTagName("price").item(0).getTextContent()))
                  .currency(currency)
                  .build();

          String supplier = element.getElementsByTagName("supplier").item(0).getTextContent();
          Product product =
              Product.builder()
                  .timeStamp(created)
                  .description(
                      (element.getElementsByTagName("description").item(0).getTextContent()))
                  .gtin(element.getElementsByTagName("gtin").item(0).getTextContent())
                  .price(price)
                  .supplier(supplier)
                  .orderid(orderId)
                  .build();

          if (suppliersProducts.containsKey(supplier)) {
            suppliersProducts.get(supplier).add(product);
          } else {
            List<Product> products = new ArrayList<>();
            products.add(product);
            suppliersProducts.put(supplier, products);
          }
        }
      }
    }
    return suppliersProducts;
  }
}
