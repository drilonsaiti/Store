package com.example.store.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ProductToSale")
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

    // Constructors, getters, and setters
}