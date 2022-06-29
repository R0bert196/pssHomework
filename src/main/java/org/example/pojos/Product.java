package org.example.pojos;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDate;

//@NoArgsConstructor
//@AllArgsConstructor
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "products")
public class Product {


    private Instant timeStamp;

    private String description;
    private String gtin;
    private Price price;
    private String supplier;

    @XmlTransient
    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }



}
