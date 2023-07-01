package com.example.store.service.imp;


import com.example.store.model.Products;
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
    public Sales addProductToSalesDay(Long id, int quantity) {
        Sales sales = this.findByDay(LocalDateTime.now());
        Products products = this.prodcutsRepository.findById(id).orElseThrow(()->new ProductNotFoundException(id));
        if (sales.getQuantityByProductId().get(products.getId()) == null){
            sales.getPricePerProduct().put(products.getId(),products.getPrice());
            sales.getPricePerPurchaseProduct().put(products.getId(), products.getPurchase_price());
        }

        sales.getQuantityByProductId().put(products.getId(), sales.getQuantityByProductId().getOrDefault(products.getId(),0) + quantity);
        this.salesRepository.save(sales);

        products.setQuantity(products.getQuantity() - quantity);
        this.prodcutsRepository.save(products);


        return sales;
    }

    @Override
    public List<ProductQuantityDto> fromMapToList(Sales sales) {
        List<ProductQuantityDto> productQuantityDtos = new ArrayList<>();
        Map<Long,Integer> map = sales.getQuantityByProductId();

        for (Map.Entry<Long, Integer> entry : map.entrySet()) {
            Products products = this.prodcutsRepository.findById(entry.getKey()).orElseThrow();
            double price = sales.getPricePerProduct().get(products.getId());
            double purchase_price = sales.getPricePerPurchaseProduct().get(products.getId());
            double pureProfit = entry.getValue() * purchase_price;
            productQuantityDtos.add(new ProductQuantityDto(products.getName(), entry.getValue(),price*entry.getValue(),pureProfit));
        }
        return productQuantityDtos;
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

    @Override
    public List<TopProductQuantityAndProfitDto> topProducts(String startDate, String endDate) {
        List<Sales> sales = new ArrayList<>();
        System.out.println("IMPL " + startDate);
        if (!startDate.isEmpty()){
            sales = this.productsByStartDate(startDate,endDate);
        }else {
            sales = this.findAll();
        }
        Map<Long,Integer> map = new HashMap<>();
        Map<Long,TopProductQuantityAndProfitDto> saleMap = new HashMap<>();

        for (Sales s : sales){
            for (Map.Entry<Long, Integer> entry : s.getQuantityByProductId().entrySet()){
                map.put( entry.getKey(),map.getOrDefault(entry.getKey(),0) + entry.getValue());
                double price = s.getPricePerProduct().get(entry.getKey());
                Products products = this.prodcutsRepository.findById(entry.getKey()).orElseThrow();
                if (!saleMap.containsKey(entry.getKey())) {
                    saleMap.put(entry.getKey(), new TopProductQuantityAndProfitDto(products.getName(), map.get(entry.getKey()), price * entry.getValue()));
                }else{
                    TopProductQuantityAndProfitDto topProduct = saleMap.get(entry.getKey());
                    double profit = topProduct.getProfit() + (price * entry.getValue());
                    topProduct = new TopProductQuantityAndProfitDto(products.getName(), map.get(entry.getKey()),profit);
                    saleMap.put(entry.getKey(), topProduct);
                }
            }

        }
        List<TopProductQuantityAndProfitDto> list = new ArrayList<>();
        for (Map.Entry<Long, TopProductQuantityAndProfitDto> entry : saleMap.entrySet()) {
            list.add(entry.getValue());
        }

        return list;
    }

}
