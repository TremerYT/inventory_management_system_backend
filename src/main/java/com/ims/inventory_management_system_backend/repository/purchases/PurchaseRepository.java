package com.ims.inventory_management_system_backend.repository.purchases;

import com.ims.inventory_management_system_backend.entities.purchase.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    
    @Query("SELECT p FROM Purchase p LEFT JOIN FETCH p.purchaseItems pi LEFT JOIN FETCH pi.product WHERE p.id = :id")
    Optional<Purchase> findByIdWithItemsAndProduct(Long id);
}
