package com.ims.inventory_management_system_backend.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class StockResponseDTO {
    private Long lowStock;
    private Long outOfStock;
}
