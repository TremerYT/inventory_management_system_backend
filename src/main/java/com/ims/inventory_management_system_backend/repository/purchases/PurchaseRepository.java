package com.ims.inventory_management_system_backend.repository.purchases;

import com.ims.inventory_management_system_backend.entities.purchase.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
