package com.example.store.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "Sales")
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Key - Product id,value - quantity (sales for that product)
    @Column(name = "quantityByProductId", length = 999)
    @ElementCollection(targetClass=Integer.class)
    @MapKeyColumn(name="Product_Id")
    private Map<Long, Integer> quantityByProductId;

    @Column(name = "pricePerProdcuts", length = 999)
    @ElementCollection(targetClass=Double.class)
    @MapKeyColumn(name="Product_Id")
    private Map<Long, Double> pricePerProduct;

    @Column(name = "pricePerProdcuts", length = 999)
    @ElementCollection(targetClass=Double.class)
    @MapKeyColumn(name="Product_Id")
    private Map<Long, Double> pricePerPurchaseProduct;

    @Column(name = "open", length = 999)
    private LocalDateTime open;
    @Column(name = "closed", length = 999)

    private LocalDateTime closed;

    public Sales() {
        this.quantityByProductId = new HashMap<>();
        this.open = LocalDateTime.now();
        this.closed = null;
    }

    public String getOpenDayString(){
        return String.format("%d.%d.%d",this.open.getDayOfMonth(),this.open.getMonth().getValue(),this.open.getYear());
    }
    public String getClosedDayString(){
        return String.format("%d:%d",this.closed.getHour(),this.closed.getMinute());
    }

    public String getTimeOpenString(){
        return String.format("%d:%d",this.open.getHour(),this.open.getMinute());
    }
}
