package com.ims.inventory_management_system_backend.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryRequestDTO {
    private Long id;

    @NotBlank(message = "Category name is required")
    private String categoryName;

    @NotNull
    private Boolean isActive;
}