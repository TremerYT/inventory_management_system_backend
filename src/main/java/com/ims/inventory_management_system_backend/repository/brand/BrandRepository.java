package com.ims.inventory_management_system_backend.repository.brand;

import com.ims.inventory_management_system_backend.entities.brands.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
