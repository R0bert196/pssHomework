package org.example.xmlHandlers;

import lombok.extern.slf4j.Slf4j;
import org.example.configs.Config;
import org.example.pojos.Product;
import org.example.pojos.Products;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Slf4j
public class XmlCreator {

  public void productToXML(Map<String, List<Product>> suppliersProducts, int fileId) {
    Map<String, String> configProperties = Config.getConfigProperties();
    String outputPath = configProperties.get("outputPath");

    try {
      Marshaller jaxbMarshaller = getMarshaller();
      StringWriter stringWriter = new StringWriter();

      for (String key : suppliersProducts.keySet()) {
        List<Product> productsList = suppliersProducts.get(key);
        productsList.sort(
            Comparator.comparing(Product::getTimeStamp)
                .thenComparing(p -> p.getPrice().getPrice())
                .reversed());
        Products products = new Products(productsList);

        jaxbMarshaller.marshal(products, stringWriter);

        String xmlContent = stringWriter.toString();
        log.info("\n" + xmlContent);
        Files.createDirectories(Paths.get(outputPath + "/order" + fileId));
        File file = new File(outputPath + "/order" + fileId + "/" + key + fileId + ".xml");

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
