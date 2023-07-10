package com.example.store.controllers;

import com.example.store.model.Sale;
import com.example.store.model.Sales;
import com.example.store.model.dto.TopProductQuantityAndProfitDto;
import com.example.store.service.ProductsService;
import com.example.store.service.SalesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sales")
public class SalesController {

    private final SalesService salesService;
    private final ProductsService productsService;

    public SalesController(SalesService salesService, ProductsService productsService) {
        this.salesService = salesService;
        this.productsService = productsService;
    }

    @GetMapping
    public ResponseEntity<Sales> getSales() {
        Sales sales = this.salesService.findByDay(LocalDateTime.now());
        if (sales != null) {
            return ResponseEntity.ok(sales);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Sales> addSales(@RequestBody Sale sale) {
        Sales sales = this.salesService.addSale(sale);
        return ResponseEntity.ok(sales);
    }

    @PostMapping("/open-store")
    public ResponseEntity<Void> openStore() {
        this.salesService.openDay();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/close-store")
    public ResponseEntity<Void> closeStore() {
        this.salesService.closeDay();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all-sales")
    public ResponseEntity<List<Sales>> getAllSales() {
        List<Sales> sales = this.salesService.findAll().stream()
                .sorted(Comparator.comparing(Sales::getClosed).reversed())
                .collect(Collectors.toList());
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/all-sales-data/{day}")
    public ResponseEntity<Sales> getAllSalesPerDay(@PathVariable String day) {
        LocalDateTime time = LocalDateTime.parse(day);
        Sales sales = this.salesService.findByDay(time);
        if (sales != null) {
            return ResponseEntity.ok(sales);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

   /* @GetMapping("/top-products")
    public ResponseEntity<List<TopProductQuantityAndProfitDto>> getTopProductsByProfit() {
        List<TopProductQuantityAndProfitDto> topProducts = this.salesService.topProducts("", "").stream()
                .sorted(Comparator.comparing(TopProductQuantityAndProfitDto::getProfit).reversed())
                .collect(Collectors.toList());
        return ResponseEntity.ok(topProducts);
    }*/

    /*@PostMapping("/top-products-date")
    public ResponseEntity<List<TopProductQuantityAndProfitDto>> getTopProductsByProfit(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        List<TopProductQuantityAndProfitDto> topProducts = this.salesService.topProducts(startDate, endDate).stream()
                .sorted(Comparator.comparing(TopProductQuantityAndProfitDto::getProfit).reversed())
                .collect(Collectors.toList());
        return ResponseEntity.ok(topProducts);
    }*/
}
