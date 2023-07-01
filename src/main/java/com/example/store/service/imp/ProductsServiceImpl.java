package com.example.store.service.imp;

import com.example.store.model.Products;
import com.example.store.model.Sales;
import com.example.store.model.dto.ProductsByDateDto;
import com.example.store.model.exceptions.NotFoundException;
import com.example.store.model.exceptions.ProductNotFoundException;
import com.example.store.repository.ProductsRepository;
import com.example.store.repository.SalesRepository;
import com.example.store.service.ProductsService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductsService {
	private final ProductsRepository productsRepository;

	private final SalesRepository salesRepository;

	public ProductsServiceImpl(ProductsRepository productsRepository, SalesRepository salesRepository) {
		super();
		this.productsRepository = productsRepository;
		this.salesRepository = salesRepository;
	}

	@Override
	public void createProduct(String name, int price, int quantity) {
		this.productsRepository.insertProduct(name,price,quantity);
	}

	@Override
	public Products updateProduct(Long id, String name, int price, int quantity) {
		this.productsRepository.updateProduct(id,name,price,quantity);
		return this.productsRepository.findById(id).orElseThrow();
	}

	@Override
	public void deleteProduct(Long id) {
		this.productsRepository.deleteProduct(id);
	}

	public Products save(Products product) throws Exception {

		if (productsRepository.findByName(product.getName()).size() > 0) {
			throw new Exception("Product already exist");
		}
		this.productsRepository.save(product);

		return product;

	}

	public List<Products> getAll() throws NotFoundException {

		List<Products> products = productsRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		if (products.size() < 1) {
			throw new NotFoundException("No product");
		}
		return products;
	}

	@Transactional
	public Products update(Long id, Products product) throws NotFoundException {

		Optional<Products> products = productsRepository.findById(id);
		if (!products.isPresent()) {
			throw new NotFoundException("Product does not exist id : " + id);
		}
		productsRepository.save(product);

		return product;

	}

	public Optional<Products> getOne(Long id) throws NotFoundException {

		Optional<Products> product = productsRepository.findById(id);
		if (!product.isPresent()) {
			throw new NotFoundException("Product does not exist id : " + id);
		}
		return product;

	}

	@Override
	public Optional<Products> getById(Long id) throws NotFoundException {
		return Optional.of(productsRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id)));
	}

	public Boolean delete(Long id) throws NotFoundException {

		Optional<Products> product = productsRepository.findById(id);
		if (!product.isPresent()) {
			throw new NotFoundException("Product does not exist id : " + id);
		}
		productsRepository.deleteById(id);
		return true;

	}

	public List<Products> SearchProductsByName(String name) throws NotFoundException {
		List<Products> products = productsRepository.SearchProductsByName(name.trim());
		if (products.size() < 1) {
			throw new NotFoundException("Product don't already exist");
		}

		return products;
	}

	public List<ProductsByDateDto> productsByDate(Long id, String name) {
		List<ProductsByDateDto> list = new ArrayList<>();
		List<Sales> sales = this.salesRepository.findAll().stream().filter(s -> s.getQuantityByProductId().containsKey(id)).collect(Collectors.toList());
		for (Sales s : sales){
			System.out.println(s);
			int quantity = s.getQuantityByProductId().get(id);
			double price = s.getPricePerProduct().get(id);
			double purchase_price = s.getPricePerPurchaseProduct().get(id);
			list.add(new ProductsByDateDto(name,s.getOpen(),quantity,quantity*price,quantity * purchase_price));
		}
		return list;
	}
}
