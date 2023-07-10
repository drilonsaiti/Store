package com.example.store.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "Sales")
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "sales_id")
    private List<Sale> sales;

    private double totalProfit;

    private double pureProfit;

    @Column(name = "open", length = 999)
    private LocalDateTime open;
    @Column(name = "closed", length = 999)

    private LocalDateTime closed;

    public Sales() {
        this.sales = new ArrayList<>();
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
