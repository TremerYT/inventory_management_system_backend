package com.ims.inventory_management_system_backend.repository.product;

import com.ims.inventory_management_system_backend.entities.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
