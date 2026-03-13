package com.ims.inventory_management_system_backend.dto.purchases;

import lombok.Data;

@Data
public class PurchaseItemsRequestDTO {
    private Long productId;
    private Integer quantity;
    private Double unitPrice;
    private Double discount;
}
