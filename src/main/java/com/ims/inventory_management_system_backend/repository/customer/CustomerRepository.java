package com.ims.inventory_management_system_backend.repository.customer;

import com.ims.inventory_management_system_backend.entities.customers.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT COUNT(c) FROM Customer c WHERE CAST(c.createdAt AS date) = CURRENT_DATE")
    long countCustomerCreatedToday();
    
    @Query("SELECT COUNT(c) FROM Customer c")
    long countTotalCustomers();
}
