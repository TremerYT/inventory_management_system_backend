package com.ims.inventory_management_system_backend.dto.brand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BrandRequestDTO {
    private Long id;

    @NotBlank(message = "Brand name is required")
    private String brandName;

    @NotBlank(message = "Brand image is required")
    private String brandImage;

    @NotNull
    private Boolean isActive;
}
