package com.ims.inventory_management_system_backend.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CustomerRequestDTO {
    @NotBlank(message = "First Name is required")
    @Size(min = 2, max = 50, message = "First Name must be between 2 and 50 Characters")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    @Size(min = 2, max = 50, message = "Last Name must be between 2 and 50 Characters")
    private String lastName;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^(?:\\+254|254|0)(7\\d{8}|1\\d{8})$", message = "Invalid Kenyan Phone number")
    private String phoneNumber;

    private final Boolean isActive = true;
}
