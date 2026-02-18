package com.ims.inventory_management_system_backend.dto.sales;

import lombok.Data;

@Data
public class SaleItemsRequestDTO {
    private Long productId;
    private Integer quantity;
    private Double unitPrice;
    private Double discount;
}
