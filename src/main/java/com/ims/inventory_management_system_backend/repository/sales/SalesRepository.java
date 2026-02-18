package com.ims.inventory_management_system_backend.repository.sales;

import com.ims.inventory_management_system_backend.entities.sale.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sales, Long> {
}
