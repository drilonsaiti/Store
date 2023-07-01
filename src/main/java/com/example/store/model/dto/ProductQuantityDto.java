package com.example.store.model.dto;

public class ProductQuantityDto {

    String name;
    int quantity;

    double profit;
    double pure_profit;

    public ProductQuantityDto(String name, int quantity,double profit,double pure_profit) {
        this.name = name;
        this.quantity = quantity;
        this.profit = profit;
        this.pure_profit = pure_profit;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getProfit() {
        return profit;
    }

    public double getPure_profit() {
        return pure_profit;
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
