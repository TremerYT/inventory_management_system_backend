package com.ims.inventory_management_system_backend.repository.brand;

import com.ims.inventory_management_system_backend.entities.brands.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    boolean existsByBrandName(String brandName);

    @Query("SELECT COUNT(b) FROM Brand b")
    Long countAllBrands ();

    Long id(Long id);
}
