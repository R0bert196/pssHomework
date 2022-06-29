package org.example.pojos;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@XStreamAlias("Order")
public class Order {

    @XStreamAsAttribute
    private LocalDate created;

    @XStreamAsAttribute
    private long ID;

    @XStreamImplicit
    private List<Product> products;

}
