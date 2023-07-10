package com.example.store.controllers;

import com.example.store.model.ProductToSale;
import com.example.store.model.Products;
import com.example.store.model.dto.ProductToSaleDto;
import com.example.store.service.ProductToSaleService;
import com.example.store.service.ProductsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
@RequestMapping("/product-to-sale")
public class ProductToSaleController {

    private final ProductToSaleService productToSaleService;
    private final ProductsService productsService;



    @GetMapping("/{id}")
    public ResponseEntity<ProductToSale> getProductToSaleById(@PathVariable Long id) {
        ProductToSale productToSale = productToSaleService.findById(id);
        if (productToSale != null) {
            return ResponseEntity.ok(productToSale);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ProductToSaleDto> createProductToSale(@RequestBody ProductToSaleDto productToSaleDto) {
        ProductToSale productToSale = convertToEntity(productToSaleDto);
        productToSaleService.create(productToSale);
        return ResponseEntity.status(HttpStatus.CREATED).body(productToSaleDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductToSaleDto> updateProductToSale(@PathVariable Long id, @RequestBody ProductToSaleDto productToSaleDto) {
        ProductToSale existingProductToSale = productToSaleService.findById(id);
        if (existingProductToSale == null) {
            return ResponseEntity.notFound().build();
        }

        ProductToSale updatedProductToSale = convertToEntity(productToSaleDto);
        productToSaleService.update(updatedProductToSale);
        return ResponseEntity.ok(productToSaleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductToSale(@PathVariable Long id) {
        productToSaleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private ProductToSale convertToEntity(ProductToSaleDto productToSaleDto) {
        // Convert ProductToSaleDto to ProductToSale entity
        Products product = this.productsService.getById(productToSaleDto.getProductId()).orElseThrow();

        return new ProductToSale(product,productToSaleDto.getPrice(), productToSaleDto.getPurchasePrice());
    }
}


