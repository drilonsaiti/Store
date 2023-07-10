package com.example.store.service;

import com.example.store.model.Sale;
import com.example.store.model.dto.SaleDto;

public interface SaleService {

    Sale findById(Long id);
    Sale create(SaleDto sale);
    Sale update(Long id,SaleDto sale);

    void delete(Long id);
}
