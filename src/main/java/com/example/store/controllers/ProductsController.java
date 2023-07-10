package com.example.store.controllers;

import com.example.store.model.Products;
import com.example.store.model.dto.ProductsDto;
import com.example.store.model.exceptions.NotFoundException;
import com.example.store.service.ProductsService;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public ResponseEntity<List<Products>> getProducts() {
        try {
            List<Products> productsList = this.productsService.getAll();
            return ResponseEntity.ok(productsList);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> addProducts(@RequestBody ProductsDto productsDto) {
        try {
            Products products = new Products(productsDto.getName(), productsDto.getPrice(), productsDto.getPurchasePrice(), productsDto.getQuantity(), productsDto.getBarCode());
            this.productsService.createProduct(products);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Products> getProductById(@PathVariable Long id) {
        try {
            Optional<Products> product = this.productsService.getById(id);
            if (product.isPresent()) {
                return ResponseEntity.ok(product.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Products> updateProduct(@PathVariable Long id, @RequestBody ProductsDto productsDto) {
        try {
            Products products = new Products(productsDto.getName(), productsDto.getPrice(), productsDto.getPurchasePrice(), productsDto.getQuantity(), productsDto.getBarCode());
            Products updatedProduct = this.productsService.updateProduct(id, products);
            return ResponseEntity.ok(updatedProduct);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            this.productsService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
