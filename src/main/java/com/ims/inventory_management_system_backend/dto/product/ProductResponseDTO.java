package com.ims.inventory_management_system_backend.dto.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductResponseDTO {
    private String skuNumber;
    private String barcodeNumber;
    private String productName;
    private String brand;
    private String description;
    private Integer quantity;
    private Double costPrice;
    private Double unitPrice;
    private Integer minStock;
    private String discountType;
    private Double discountValue;
    private String categoryName;
    private String mainImage;
    private List<String> galleryImages;
}
