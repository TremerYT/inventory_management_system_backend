package com.ims.inventory_management_system_backend.repository.customer;

import com.ims.inventory_management_system_backend.entities.customers.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
