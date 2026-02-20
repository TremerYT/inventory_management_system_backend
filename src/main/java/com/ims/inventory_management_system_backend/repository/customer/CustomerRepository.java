package com.ims.inventory_management_system_backend.repository.customer;

import com.ims.inventory_management_system_backend.entities.customers.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT COUNT(c) FROM Customer c WHERE DATE(c.createdAt) == CURRENT_DATE ")
    long countCustomerCreatedToday();
}
