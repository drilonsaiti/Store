package com.example.store.service;

import com.example.store.model.Sale;
import com.example.store.model.Sales;
import com.example.store.model.dto.ProductQuantityDto;
import com.example.store.model.dto.TopProductQuantityAndProfitDto;

import java.time.LocalDateTime;
import java.util.List;

public interface SalesService {

    List<Sales> findAll();

    Sales findByDay(LocalDateTime time);

    Sales openDay();
    Sales closeDay();
    Sales addSale(Sale sale);
    List<Sales> productsByStartDate(String startDate,String endDate);
}
