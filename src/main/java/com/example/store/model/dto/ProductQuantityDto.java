package com.example.store.model.dto;

public class ProductQuantityDto {

    String name;
    int quantity;

    int profit;

    public ProductQuantityDto(String name, int quantity,int profit) {
        this.name = name;
        this.quantity = quantity;
        this.profit = profit;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getProfit() {
        return profit;
    }

    @Override
    public String toString() {
        return "ProductQuantityDto{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", profit=" + profit +
                '}';
    }
}
