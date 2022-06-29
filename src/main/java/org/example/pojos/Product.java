package org.example.pojos;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@XStreamAlias("product")
public class Product {

    private String description;
    private long gtin;
    private Price price;
    private String supplier;

}
