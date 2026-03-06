package com.ims.inventory_management_system_backend.dto.purchases;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurchaseItemsResponseDTO {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double unitPrice;
    private Double discount;
    private Double subTotal;
}
