package org.example.pojos;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Product {


    @XmlTransient
    private Instant timeStamp;

    private String description;
    private String gtin;
    private Price price;
    @XmlTransient
    private String supplier;
    private long orderid;

//    @XmlTransient
//    public void setTimeStamp(Instant timeStamp) {
//        this.timeStamp = timeStamp;
//    }



}
