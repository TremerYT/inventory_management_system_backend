package com.ims.inventory_management_system_backend.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ProductRequestDTO {
    @NotBlank(message = "Sku Number is Required")
    private String skuNumber;

    @NotBlank(message = "Barcode Number is Required")
    private String barcodeNumber;

    @NotBlank(message = "Product Name is Required")
    private String productName;

    @NotNull(message = "Category is required")
    private Long categoryId;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotBlank(message = "Unit is required")
    private String productUnit;

    @NotBlank(message = "Description is Required")
    private String description;

    @NotBlank(message = "Quantity is Required")
    private Integer quantity;

    @NotBlank(message = "Cost price is required")
    private Double costPrice;

    @NotBlank(message = "unit price is required")
    private Double unitPrice;

    @NotBlank(message = "Minimum Stock is Required")
    private Integer minStock;

    @NotBlank(message = "Discount Type is Required")
    private String discountType;

    @NotBlank(message = "Discount value is required")
    private Double discountValue;

    @NotBlank(message = "Product image is required")
    private String mainImage;

    private List<String> galleryImages;
}
