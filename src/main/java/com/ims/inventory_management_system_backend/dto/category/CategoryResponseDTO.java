package com.ims.inventory_management_system_backend.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data

public class CategoryResponseDTO {
    private Long id;
    private String categoryCode;
    private String categoryName;
    private String categoryImage;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
