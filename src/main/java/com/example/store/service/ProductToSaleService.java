package com.example.store.service;

import com.example.store.model.ProductToSale;

public interface ProductToSaleService {

    ProductToSale findById(Long id);
    ProductToSale create(ProductToSale productToSale);
    ProductToSale update(ProductToSale productToSale);
    void deleteById(Long id);
}
