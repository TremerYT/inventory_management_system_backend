package com.ims.inventory_management_system_backend.repository.product;

import com.ims.inventory_management_system_backend.entities.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.quantity > 0 AND p.quantity <= p.minStock")
    List<Product> findLowStockProducts();

    @Query("SELECT p FROM Product p WHERE p.quantity = 0")
    List<Product> findOutOfStockProducts();

    @Query("SELECT COUNT(p) FROM Product p WHERE p.quantity > 0 AND p.quantity <= p.minStock")
    Long countLowStockProducts();

    @Query("SELECT COUNT(p) FROM Product p WHERE p.quantity = 0")
    Long countOutOfStockProducts();
}
