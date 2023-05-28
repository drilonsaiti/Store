package com.example.store.model.dto;

import com.example.store.model.Sales;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalesResponseDto {

    private Sales sales;
    private String date;
    private String time;
}
