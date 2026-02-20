package com.ims.inventory_management_system_backend.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO {
    private Long id;
    private String customerCode;
    private String firstName;
    private String lastName;
    private String customerCategory;
    private String email;
    private String phoneNumber;
    private String country;
    private String city;
    private String zipCode;
    private Integer rewardPoints;
    private String address;
    private Boolean isActive;
}