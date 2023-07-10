package com.example.store.service.imp;

import com.example.store.model.ProductToSale;
import com.example.store.repository.ProductToSaleRepository;
import com.example.store.service.ProductToSaleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductToSaleServiceImpl implements ProductToSaleService {

    private final ProductToSaleRepository product;

    public ProductToSaleServiceImpl(ProductToSaleRepository product) {
        this.product = product;
    }

    @Override
    public ProductToSale findById(Long id) {
        return this.product.findById(id).orElseThrow();
    }

    @Override
    public ProductToSale create(ProductToSale productToSale) {
        return this.product.save(productToSale);
    }

    @Override
    public ProductToSale update(ProductToSale productToSale) {
        ProductToSale existingProduct = this.findById(productToSale.getId());
        if (existingProduct == null) {
            return null;
        }

        existingProduct.setDate(LocalDateTime.now());
        existingProduct.setProduct(productToSale.getProduct());
        existingProduct.setPrice(productToSale.getPrice());
        existingProduct.setPurchasePrice(productToSale.getPurchasePrice());

        return this.product.save(existingProduct);

    }

    @Override
    public void deleteById(Long id) {
        this.product.deleteById(id);
    }


}
