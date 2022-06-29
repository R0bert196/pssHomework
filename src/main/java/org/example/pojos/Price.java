package org.example.pojos;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Price {

    private String currency;
    private float  price;

    @XmlAttribute
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @XmlElement(name = "price")
    public void setPrice(float price) {
        this.price = price;
    }


}
