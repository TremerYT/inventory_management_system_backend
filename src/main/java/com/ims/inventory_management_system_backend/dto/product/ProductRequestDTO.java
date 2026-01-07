package com.ims.inventory_management_system_backend.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ProductRequestDTO {
    @NotBlank(message = "Sku Number is Required")
    private String skuNumber;

    @NotBlank(message = "Product Name is Required")
    private String productName;

    @NotNull(message = "Category is required")
    private Long categoryId;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotBlank(message = "Description is Required")
    private String description;

    @NotBlank(message = "Quantity is Required")
    private Integer quantity;

    @NotBlank(message = "Price is required")
    private Double price;

    @NotBlank(message = "Discount Type is Required")
    private String discountType;

    @NotBlank(message = "Discount value is required")
    private Double discountValue;

    @NotBlank(message = "Product images are required")
    private List<String> productImages;
}
