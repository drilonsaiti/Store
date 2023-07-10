package com.example.store.controllers;

import com.example.store.model.Sale;
import com.example.store.model.dto.SaleDto;
import com.example.store.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sale")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable Long id) {
        Sale sale = saleService.findById(id);
        if (sale != null) {
            return ResponseEntity.ok(sale);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Sale> createSale(@RequestBody SaleDto saleDto) {
        Sale createdSale = saleService.create(saleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSale);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleDto> updateSale(@PathVariable Long id, @RequestBody SaleDto saleDto) {
        Sale existingSale = saleService.findById(id);
        if (existingSale == null) {
            return ResponseEntity.notFound().build();
        }


        this.saleService.update(id,saleDto);

        return ResponseEntity.ok(saleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        saleService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

