package com.example.store.model.dto;

import com.example.store.model.ProductToSale;
import com.example.store.model.Products;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDto {
    private List<ProductToSaleDto> products;
    private double total;
}
