package com.ims.inventory_management_system_backend.dto.supplier;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SupplierRequestDTO {
    @NotBlank(message = "First Name is required")
    @Size(max = 50, message = "First Name must not be more than 50 characters")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    @Size(max = 50, message = "Last Name must not be more than 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Zip Code is required")
    private String zipCode;

    @NotBlank(message = "Address is required")
    private String address;

    private Boolean isActive;
}
