package org.example.pojos;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDate;

//@NoArgsConstructor
//@AllArgsConstructor
@Data
@Builder
@XStreamAlias("product")
public class Product {

    private Instant timeStamp;

    private String description;
    private String gtin;
    private Price price;
    private String supplier;

}
