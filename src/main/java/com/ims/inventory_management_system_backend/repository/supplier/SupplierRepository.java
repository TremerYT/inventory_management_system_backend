package com.ims.inventory_management_system_backend.repository.supplier;

import com.ims.inventory_management_system_backend.entities.supplier.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    @Query("SELECT COUNT(s) FROM Supplier s WHERE CAST(s.createdAt AS date) = CURRENT_DATE")
    long countSupplierCreatedToday();
}
