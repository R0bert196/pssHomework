package org.example.xmlHandlers;

import org.example.configs.Config;
import org.example.pojos.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.*;
import java.time.Instant;
import java.util.*;

public class XmlParser {
    private final DocumentBuilderFactory factory;


    public XmlParser(DocumentBuilderFactory factory) {
        this.factory = factory;
    }

    /**
     * parseOrderXml is the main method responsible for the action flow of parsing the xml
     * and creating a file for each supplier found.
     *
     * @param fileName - the name of the file added to the input folder
     * @return
     */
    //sa il numesc xmlToObject?
    public Map<String, List<Product>> parseOrderXml(String fileName) throws IllegalAccessException {

        try {
        //Astea sa le scot la nivelul clasei? Le repet si in metoda de mai jos
        Map<String, String> configProperties = Config.getConfigProperties();
        String inputPath = configProperties.get("inputPath");

        File xmlFile = new File(inputPath + fileName);

            this.factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            NodeList orderNodes = getOrderNodes(xmlFile);
            return getProducts(orderNodes);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
            throw new IllegalAccessException();
        }

    }



    public void main(String fileName)  {
        int fileId = validateFilename(fileName);
        Map<String, List<Product>> suppliersProducts;
        try {
            suppliersProducts = parseOrderXml(fileName);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        productToXML(suppliersProducts, fileName, fileId);

    }

    private int validateFilename(String fileName) {
        try {
            return Integer.parseInt(fileName.substring(6, 8));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Incorrect file name, file isn't of type orders##.xml");
        }
    }

    private NodeList getOrderNodes(File xmlFile) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName("order");
    }


    private Map<String, List<Product>> getProducts(NodeList orderNodes) {
        Map<String, List<Product>> suppliersProducts = new HashMap<>();

        //orders nodes
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

//            products nodes
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

                    String supplier = element.getElementsByTagName("supplier").item(0).getTextContent();
                    Product product = Product.builder()
                            .timeStamp(created)
                            .description((element.getElementsByTagName("description").item(0).getTextContent()))
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



    private void productToXML(Map<String, List<Product>> suppliersProducts, String fileName, int fileId) {
        Map<String, String> configProperties = Config.getConfigProperties();
        String outputPath = configProperties.get("outputPath");

        try {
            Marshaller jaxbMarshaller = getMarshaller();
            StringWriter sw = new StringWriter();

            for (String key : suppliersProducts.keySet()) {
                List<Product> productsList = suppliersProducts.get(key);
                productsList.sort(Comparator.comparing(Product::getTimeStamp).thenComparing(p -> p.getPrice().getPrice()).reversed());
                Products products = new Products(productsList);


                jaxbMarshaller.marshal(products, sw);

                String xmlContent = sw.toString();
                System.out.println(xmlContent);
                Files.createDirectories(Paths.get(outputPath + "order" + fileId));
                File file = new File(outputPath + "order" + fileId + "/" + fileName + fileId + ".xml");

                jaxbMarshaller.marshal(products, file);

            }
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }

    }

    private Marshaller getMarshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Products.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        return jaxbMarshaller;
    }


}
