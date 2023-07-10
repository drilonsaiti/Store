package com.example.store.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ProductToSale")
@Data
@NoArgsConstructor

public class ProductToSale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Products product;

    private LocalDateTime date;

    private double price;

    private double purchasePrice;

    public ProductToSale(Products product, double price, double purchasePrice) {
        this.product = product;
        this.date = LocalDateTime.now();
        this.price = price;
        this.purchasePrice = purchasePrice;
    }
}