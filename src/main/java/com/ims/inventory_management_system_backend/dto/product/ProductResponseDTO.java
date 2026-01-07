package com.ims.inventory_management_system_backend.dto.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductResponseDTO {
    private String skuNumber;
    private String productName;
    private String brand;
    private String description;
    private Integer quantity;
    private Double price;
    private String discountType;
    private Double discountValue;
    private String categoryName;
    private List<String> productImages;
}
