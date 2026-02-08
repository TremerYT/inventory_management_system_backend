package com.ims.inventory_management_system_backend.dto.brand;

import java.time.LocalDateTime;

public class BrandResponseDTO {
    private Long id;
    private String brandCode;
    private String brandName;
    private String brandImage;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
