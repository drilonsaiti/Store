package com.example.store.repository;

import com.example.store.model.Products;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {

	List<Products> findByName(String name);

	Products findByNameContainingIgnoreCase(String name);

	@Modifying
	@Query(value = "INSERT INTO Products (name, price, quantity) VALUES (:name, :price, :quantity)", nativeQuery = true)
	@Transactional
	void insertProduct(@Param("name") String name, @Param("price") int price, @Param("quantity") int quantity);


	@Modifying
	@Query("UPDATE Products p SET p.name = :name, p.price = :price, p.quantity = :quantity WHERE p.id = :id")
	@Transactional
	void updateProduct(@Param("id") Long id, @Param("name") String name, @Param("price") int price, @Param("quantity") int quantity);

	@Modifying
	@Query("DELETE FROM Products p WHERE p.id = :id")
	@Transactional
	void deleteProduct(@Param("id") Long id);

	@Query("select b from Products b where b.name like %:name%")
	List<Products> SearchProductsByName(String name);
}
