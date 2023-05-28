package com.example.store.service;



import com.example.store.model.Products;
import com.example.store.model.dto.ProductsByDateDto;
import com.example.store.model.exceptions.NotFoundException;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductsService {
	public Products save(Products products) throws Exception;

	public List<Products> getAll() throws NotFoundException;

	public Products update(Long id, Products products) throws NotFoundException;

	public Optional<Products> getOne(Long id) throws NotFoundException;

	public Optional<Products> getById(Long id) throws NotFoundException;

	public Boolean delete(Long id) throws NotFoundException;

	public List<Products> SearchProductsByName(String name) throws NotFoundException;

	public List<ProductsByDateDto> productsByDate(Long id, String name);

	void createProduct(String name,int price,int quantity);
	 Products updateProduct(Long id, String name, int price, int quantity);

	void deleteProduct(Long id);

}
