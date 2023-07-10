package com.example.store.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductToSaleDto {
    private Long id;
    private Long productId;
    private LocalDateTime date;
    private double price;
    private double purchasePrice;
}
