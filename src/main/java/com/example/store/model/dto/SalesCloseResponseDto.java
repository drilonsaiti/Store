package com.example.store.model.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SalesCloseResponseDto {
    private List<ProductQuantityDto> sales;
    private String dateOpen;
    private String time;
    private String dateClosed;
}
