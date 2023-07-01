package com.example.store.model.dto;

import java.time.LocalDateTime;

public class ProductsByDateDto {

    String name;
    LocalDateTime day;
    int quantity;

    double profit;

    double pureProfit;

    public ProductsByDateDto(String name,LocalDateTime day, int quantity, double profit,double pureProfit) {
        this.day = day;
        this.quantity = quantity;
        this.profit = profit;
        this.pureProfit = pureProfit;
    }

    public LocalDateTime getDay() {
        return day;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getProfit() {
        return profit;
    }

    public double getPureProfit() {
        return pureProfit;
    }

    public String getDate(){
        String dayString = this.day.getDayOfWeek().toString();
        String monthString = this.day.getMonth().toString();
        int dayNum = this.day.getDayOfMonth();
        int years = this.day.getYear();
        return String.format("%s %s %d %d",dayString,monthString.substring(0,3),dayNum,years);
    }
}
