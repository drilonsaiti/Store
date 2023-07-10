package com.example.store.model.dto;

import lombok.Data;

@Data
public class ProductsDto {
    private String name;
    private double price;
    private double purchasePrice;
    private int quantity;
    private Long barCode;

    public ProductsDto(String name, double price, double purchasePrice, int quantity, Long barCode) {
        this.name = name;
        this.price = price;
        this.purchasePrice = purchasePrice;
        this.quantity = quantity;
        this.barCode = barCode;
    }
}
