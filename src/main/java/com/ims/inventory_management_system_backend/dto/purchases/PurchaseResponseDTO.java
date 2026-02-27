package com.ims.inventory_management_system_backend.dto.purchases;

import java.time.LocalDate;
import java.util.List;

public class PurchaseResponseDTO {
    private Long id;
    private String referenceNumber;
    private LocalDate date;
    private Long supplierId;
    private String supplierName;
    private Double shipping;
    private Double paid;
    private String purchaseStatus;
    private String remarks;
    private Double subTotal;
    private Double grandTotal;
    private List<PurchaseItemsResponseDTO> items;
}
