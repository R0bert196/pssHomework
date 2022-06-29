package org.example.xmlHandlers;

import org.example.pojos.Order;
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
            getOrders(orderNodes);
//            for (int i = 0; i < orderNodes.getLength(); i++) {
//                Node orderNode = orderNodes.item(i);
//                if (orderNode.getNodeType() == Node.ELEMENT_NODE) {
//                    Element orderElement =  (Element) orderNode;
//                    System.out.println(orderElement.getAttribute("created"));
//                    System.out.println(orderNode.getChildNodes().getLength());
//
//                }
//            }



        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

    }

    private List<Order> getOrders(NodeList orderNodes) {

        //orders nodes
        for (int i = 0; i < orderNodes.getLength(); i++) {
            Node orderNode = orderNodes.item(i);
//            System.out.println(orderNode.getNodeName());
            NodeList productNodes = orderNode.getChildNodes();

//            products nodes
            for (int j = 0; j < productNodes.getLength(); j++) {
                Node productNode = productNodes.item(j);
                if (productNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) productNode;
                    System.out.println(element.getElementsByTagName("description").item(0).getTextContent());
//                    System.out.println(productNode.getNodeName());

                }
            }

        }
        return null;
    }

}
