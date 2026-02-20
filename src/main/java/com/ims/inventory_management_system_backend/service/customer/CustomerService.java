package com.ims.inventory_management_system_backend.service.customer;

import com.ims.inventory_management_system_backend.dto.customer.CustomerRequestDTO;
import com.ims.inventory_management_system_backend.dto.customer.CustomerResponseDTO;
import com.ims.inventory_management_system_backend.entities.customers.Customer;
import com.ims.inventory_management_system_backend.repository.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public String generateCustomerCode() {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long todayCount = customerRepository.countCustomerCreatedToday();
        String sequence = String.format("%03d", todayCount + 1);

        return "CUST-" + today + sequence;
    }

    public CustomerResponseDTO createCustomer(CustomerRequestDTO request) {
        Customer newCustomer = new Customer();
        mapRequestToCustomer(newCustomer, request);
        Customer savedCustomer = customerRepository.save(newCustomer);
        return mapCustomerToResponse(savedCustomer);
    }

    public List<CustomerResponseDTO> getAllCustomers() {
        return customerRepository
                .findAll()
                .stream()
                .map(this::mapCustomerToResponse)
                .toList();
    }

    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO request) {
        Customer customerToBeUpdated = customerRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Customer does not exist"));
        mapRequestToCustomer(customerToBeUpdated, request);
        Customer updatedCustomer = customerRepository.save(customerToBeUpdated);
        return mapCustomerToResponse(updatedCustomer);
    }

    public void deleteCustomer (Long id) {
        customerRepository.deleteById(id);
    }

    private void mapRequestToCustomer(Customer customer, CustomerRequestDTO request) {
        String customerCode = generateCustomerCode();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setCustomerCode(customerCode);
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setCountry(request.getCountry());
        customer.setCity(request.getCity());
        customer.setZipCode(request.getZipCode());
        customer.setRewardPoints(request.getRewardPoints() != null ? request.getRewardPoints() : 0);
        customer.setAddress(request.getAddress());
        customer.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
    }

    private CustomerResponseDTO mapCustomerToResponse(Customer customer) {
        return CustomerResponseDTO
                .builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .customerCode(customer.getCustomerCode())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .country(customer.getCountry())
                .city(customer.getCity())
                .zipCode(customer.getZipCode())
                .rewardPoints(customer.getRewardPoints())
                .address(customer.getAddress())
                .isActive(customer.getIsActive())
                .build();
    }
}
