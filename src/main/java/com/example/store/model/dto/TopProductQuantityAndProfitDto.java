package com.example.store.model.dto;

public class TopProductQuantityAndProfitDto {

    String name;
    int quantity;

    int profit;

    public TopProductQuantityAndProfitDto(String name, int quantity, int profit) {
        this.name = name;
        this.quantity = quantity;
        this.profit += profit;
    }

    public int getProfit() {
        return profit;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }
}
