package com.ims.inventory_management_system_backend.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardDTO {
    private Double totalInventoryValue;
    private Double totalSalesRevenue;
    private Double totalExpenses;
    private Double totalProfit;
    private Long totalCustomers;
    private Long totalSuppliers;
    private Long outOfStockProducts;
    private Long lowStockProducts;
}
