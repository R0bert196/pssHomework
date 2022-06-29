package org.example.pojos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class Products {

    private List<Product> products;
}
