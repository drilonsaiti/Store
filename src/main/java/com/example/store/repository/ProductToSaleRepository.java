package com.example.store.repository;

import com.example.store.model.ProductToSale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductToSaleRepository extends JpaRepository<ProductToSale,Long> {
}
