package org.example.xmlHandlers;

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
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class XmlParser {
    private final DocumentBuilderFactory factory;

    public XmlParser(DocumentBuilderFactory factory) {
        this.factory = factory;
    }

    public void parseOrderXml() {
        File xmlFile = new File("src/main/java/org/example/inputFiles/orders23.xml");

        try {
            this.factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList orderNodes = doc.getElementsByTagName("order");
            List<Product> products = getProducts(orderNodes);
            //sort by timestamp and price
            products.sort(Comparator.comparing(Product::getTimeStamp).reversed().thenComparing(p -> p.getPrice().getPrice()));

            System.out.println(products);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

    }

//    private void sortProducts(List<Product> products) {
//        products.sort(Comparator.comparing(Product::getTimeStamp).reversed().thenComparing(p -> p.getPrice().getPrice()));
//    }

    private List<Product> getProducts(NodeList orderNodes) {
        List<Product> products = new ArrayList<>();

        //orders nodes
        for (int i = 0; i < orderNodes.getLength(); i++) {
            Node orderNode = orderNodes.item(i);
            Instant created = null;
            NodeList productNodes = orderNode.getChildNodes();
            if (orderNode.getNodeType() == Node.ELEMENT_NODE) {
                Element orderElement = (Element) orderNode;
                created = Instant.parse(orderElement.getAttribute("created") + "Z");
            }


            for (int j = 0; j < productNodes.getLength(); j++) {
                Node productNode = productNodes.item(j);
                if (productNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) productNode;
                    NodeList priceNode = element.getElementsByTagName("price");
                    Element priceElement = (Element) priceNode.item(0);
                    String currency = priceElement.getAttribute("currency");

                    Price price = Price.builder()
                            .price(Float.parseFloat(element.getElementsByTagName("price").item(0).getTextContent()))
                            .currency(currency)
                            .build();

                    Product product = Product.builder()
                            .timeStamp(created)
                            .description((element.getElementsByTagName("description").item(0).getTextContent()))
                            .gtin(element.getElementsByTagName("gtin").item(0).getTextContent())
                            .price(price)
                            .supplier(element.getElementsByTagName("supplier").item(0).getTextContent())
                            .build();


                    products.add(product);
                }
            }
        }
        return products;
    }



}
