package com.ims.inventory_management_system_backend.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequestDTO {
    @NotBlank(message = "Category name is required")
    private String categoryName;

    @NotBlank(message = "Category status is required")
    private Boolean isActive;
}