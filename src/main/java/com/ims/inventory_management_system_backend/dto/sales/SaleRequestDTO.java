package com.ims.inventory_management_system_backend.dto.sales;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SaleRequestDTO {
    @NotBlank(message = "Reference number is required")
    public String referenceNumber;

    @NotNull(message = "Date is required")
    public LocalDate date;

    @NotNull(message = "Customer name is required")
    public Long customerId;

    @NotNull(message = "Shipping is required")
    public Double shipping;

    @NotNull(message = "Paid is required")
    public Double paid;

    @NotBlank(message = "Sale status is required")
    public String saleStatus;

    @NotBlank(message = "Payment status is required")
    public String paymentStatus;

    public String remarks;

    @NotNull(message = "Items are required")
    public List<SaleItemsRequestDTO> items;
}