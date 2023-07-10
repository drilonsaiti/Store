package com.example.store.service.imp;

import com.example.store.model.ProductToSale;
import com.example.store.model.Products;
import com.example.store.model.Sale;
import com.example.store.model.dto.SaleDto;
import com.example.store.repository.ProductToSaleRepository;
import com.example.store.repository.ProductsRepository;
import com.example.store.repository.SaleRepository;
import com.example.store.service.SaleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final ProductsRepository productsRepository;
    private final ProductToSaleRepository productToSaleRepository;



    public SaleServiceImpl(SaleRepository saleRepository, ProductsRepository productsRepository, ProductToSaleRepository productToSaleRepository) {
        this.saleRepository = saleRepository;
        this.productsRepository = productsRepository;
        this.productToSaleRepository = productToSaleRepository;
    }


    @Override
    public Sale findById(Long id) {
        return this.saleRepository.findById(id).orElseThrow();
    }

    @Override
    public Sale create(SaleDto sale) {

        List<ProductToSale> productToSales = sale.getProducts().stream()
                .map(productToSaleDto -> {
                    Products product = this.productsRepository.findById(productToSaleDto.getProductId()).orElseThrow();
                    ProductToSale productToSale = new ProductToSale(product, productToSaleDto.getPrice(), productToSaleDto.getPurchasePrice());
                    this.productToSaleRepository.save(productToSale);
                    return productToSale;
                })
                .toList();

        Sale createdSale = new Sale(productToSales, sale.getTotal());
        return this.saleRepository.save(createdSale);
    }



    @Override
    public Sale update(Long id,SaleDto sale) {
        Sale existingSale = this.findById(id);
        if (existingSale == null) {
            return null;
        }
        List<ProductToSale> productToSales = sale.getProducts().stream()
                .map(productToSaleDto -> {
                    Products product = this.productsRepository.findById(productToSaleDto.getProductId()).orElseThrow();
                    return new ProductToSale(product, productToSaleDto.getPrice(), productToSaleDto.getPurchasePrice());
                })
                .toList();

        existingSale.setDate(LocalDateTime.now());
        existingSale.setProducts(productToSales);
        existingSale.setTotal(sale.getTotal());

        return this.saleRepository.save(existingSale);

    }

    @Override
    public void delete(Long id) {
        this.saleRepository.deleteById(id);
    }
}
