package com.ims.inventory_management_system_backend.dto.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    @NotBlank(message = "First Name is required")
    @Size(min = 2, max = 50, message = "Full Name must be between 2 and 50 Characters")
    private String fullName;

    @NotBlank(message = "Username is required")
    @Size(min = 2, max = 50, message = "Username must be between 2 and 50 Characters")
    private String userName;

    @NotBlank(message = "Business Name is required")
    @Size(min = 2, max = 50, message = "Business Name must be between 2 and 50 Characters")
    private String businessName;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private final Boolean isActive = true;
}
