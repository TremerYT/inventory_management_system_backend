package com.ims.inventory_management_system_backend.dto.brand;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BrandResponseDTO {
    private Long id;
    private String brandCode;
    private String brandName;
    private String brandImage;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
