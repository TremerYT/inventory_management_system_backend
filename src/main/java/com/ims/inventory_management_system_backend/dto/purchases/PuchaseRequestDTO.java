package com.ims.inventory_management_system_backend.dto.purchases;

import com.ims.inventory_management_system_backend.dto.sales.SaleItemsRequestDTO;
import com.ims.inventory_management_system_backend.entities.purchase.PurchaseItems;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class PuchaseRequestDTO {
    @NotBlank(message = "Reference number is required")
    public String referenceNumber;

    @NotNull(message = "Date is required")
    public LocalDate date;

    @NotNull(message = "Supplier name is required")
    public Long supplierId;

    @NotNull(message = "Shipping is required")
    public Double shipping;

    @NotBlank(message = "Sale status is required")
    public String purchaseStatus;

    public String remarks;

    @NotNull(message = "Items are required")
    public List<PurchaseItemsRequestDTO> items;
}
