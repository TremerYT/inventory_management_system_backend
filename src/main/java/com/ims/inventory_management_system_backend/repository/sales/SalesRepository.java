package com.ims.inventory_management_system_backend.repository.sales;

import com.ims.inventory_management_system_backend.entities.sale.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SalesRepository extends JpaRepository<Sales, Long> {
    
    @Query("SELECT s FROM Sales s LEFT JOIN FETCH s.saleItems si LEFT JOIN FETCH si.product WHERE s.id = :id")
    Optional<Sales> findByIdWithItemsAndProduct(Long id);
    
    @Query("SELECT SUM(s.grandTotal) FROM Sales s")
    Double sumTotalSalesRevenue();
}
