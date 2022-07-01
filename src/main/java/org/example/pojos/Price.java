package org.example.pojos;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.*;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Price {

  private String currency;
  private float price;

  @XmlAttribute
  public void setCurrency(String currency) {
    this.currency = currency;
  }

  @XmlValue
  public void setPrice(float price) {
    this.price = price;
  }
}
