package com.example.store.service.imp;


import com.example.store.model.Products;
import com.example.store.model.Sale;
import com.example.store.model.Sales;
import com.example.store.model.dto.ProductQuantityDto;
import com.example.store.model.dto.TopProductQuantityAndProfitDto;
import com.example.store.model.exceptions.ProductNotFoundException;
import com.example.store.repository.ProductsRepository;
import com.example.store.repository.SalesRepository;
import com.example.store.service.SalesService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SalesServiceImpl implements SalesService {
    private final SalesRepository salesRepository;

    private final ProductsRepository prodcutsRepository;


    public SalesServiceImpl(SalesRepository salesRepository, ProductsRepository prodcutsRepository) {
        this.salesRepository = salesRepository;
        this.prodcutsRepository = prodcutsRepository;
    }

    @Override
    public List<Sales> findAll() {
        return this.salesRepository.findAll();
    }

    @Override
    public Sales findByDay(LocalDateTime time) {
        List<Sales> salesList =  this.findAll().stream().filter(s -> s.getOpen().getYear() == time.getYear() && s.getOpen().getMonth() == time.getMonth()
         && s.getOpen().getDayOfMonth() == time.getDayOfMonth()).collect(Collectors.toList());
        System.out.println(salesList.size());
        if (salesList.size() >= 1)
            return salesList.get(0);
        return null;
    }

    @Override
    public Sales openDay() {
        Sales sales = new Sales();
        this.salesRepository.save(sales);
        return sales;
    }

    @Override
    public Sales closeDay() {
        Sales sales = this.findByDay(LocalDateTime.now());
        sales.setClosed(LocalDateTime.now());
        this.salesRepository.save(sales);
        return sales;
    }

    @Override
    public Sales addSale(Sale sale) {
        Sales sales = this.findByDay(LocalDateTime.now());
        sales.getSales().add(sale);
        this.salesRepository.save(sales);
        return sales;
    }


    @Override
    public List<Sales> productsByStartDate(String startDate,String endDate) {

        LocalDateTime start = LocalDateTime.parse(startDate+"T00:00:00.000000");
        LocalDateTime end = LocalDateTime.parse(endDate+"T00:00:00.000000");


        return this.findAll().stream()
                .filter(s -> s.getOpen().getYear() == start.getYear()
                        && s.getOpen().getMonth().getValue() >= start.getMonth().getValue()
                        && s.getOpen().getMonth().getValue() <= end.getMonth().getValue()
                        && s.getOpen().getDayOfMonth() >= start.getDayOfMonth()
                        && s.getOpen().getDayOfMonth() <= end.getDayOfMonth())
                .collect(Collectors.toList());



    }

}
