package com.ims.inventory_management_system_backend.dto.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "Email is Required")
    @Email
    private String email;

    @NotBlank(message = "Password is Required")
    private String password;
}
