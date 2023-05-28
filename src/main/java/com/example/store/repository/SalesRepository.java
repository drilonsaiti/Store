package com.example.store.repository;



import com.example.store.model.Sales;
import com.example.store.model.dto.ProductQuantityDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales,Long> {


    @Query(value = "SELECT p.name, sq.quantity_by_product_id AS quantity, spp.price_per_prodcuts * sq.quantity_by_product_id AS profit " +
            "FROM sales_quantity_by_product_id sq " +
            "JOIN Products p ON sq.product_id = p.id " +
            "JOIN sales_price_per_product spp ON sq.sales_id = spp.sales_id AND sq.product_id = spp.product_id " +
            "WHERE sq.sales_id = :salesId", nativeQuery = true)
    List<Object[]> list(@Param("salesId") Long salesId);
}
