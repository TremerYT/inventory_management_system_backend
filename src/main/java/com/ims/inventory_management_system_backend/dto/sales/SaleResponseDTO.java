package com.ims.inventory_management_system_backend.dto.sales;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleResponseDTO {
    private Long id;
    private String referenceNumber;
    private LocalDate date;
    private Long customerId;
    private String customerName;
    private Double shipping;
    private Double paid;
    private String saleStatus;
    private String paymentStatus;
    private String remarks;
    private Double subTotal;
    private Double grandTotal;
    private List<SaleItemsResponseDTO> items;
}
