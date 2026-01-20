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
    private String unit;

    @NotBlank(message = "Description is Required")
    private String description;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @NotNull(message = "Cost price is required")
    private Double costPrice;

    @NotNull(message = "Unit price is required")
    private Double unitPrice;

    @NotNull(message = "Minimum Stock is Required")
    private Integer minStock;

    @NotBlank(message = "Discount Type is Required")
    private String discountType;

    @NotNull(message = "Discount value is required")
    private Double discountValue;

    @NotBlank(message = "Product image is required")
    private String mainImage;

    private List<String> galleryImages;
}
