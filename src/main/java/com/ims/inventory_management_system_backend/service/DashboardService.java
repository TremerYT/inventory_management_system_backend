package com.ims.inventory_management_system_backend.service;

import com.ims.inventory_management_system_backend.dto.dashboard.DashboardDTO;
import com.ims.inventory_management_system_backend.repository.customer.CustomerRepository;
import com.ims.inventory_management_system_backend.repository.expense.ExpenseRepository;
import com.ims.inventory_management_system_backend.repository.product.ProductRepository;
import com.ims.inventory_management_system_backend.repository.sales.SalesRepository;
import com.ims.inventory_management_system_backend.repository.supplier.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ProductRepository productRepository;
    private final SalesRepository salesRepository;
    private final ExpenseRepository expenseRepository;
    private final CustomerRepository customerRepository;
    private final SupplierRepository supplierRepository;

    public DashboardDTO getDashboardMetrics() {
        Double totalInventoryValue = Objects.requireNonNullElse(productRepository.calculateTotalInventoryValue(), 0.0);
        Double totalSalesRevenue = Objects.requireNonNullElse(salesRepository.sumTotalSalesRevenue(), 0.0);
        Double totalExpenses = Objects.requireNonNullElse(expenseRepository.sumTotalExpenses(), 0.0);
        Double totalProfit = totalSalesRevenue - totalExpenses;
        Long totalCustomers = customerRepository.countTotalCustomers();
        Long totalSuppliers = supplierRepository.countTotalSuppliers();
        Long outOfStockProducts = Objects.requireNonNullElse(productRepository.countOutOfStockProducts(), 0L);
        Long lowStockProducts = Objects.requireNonNullElse(productRepository.countLowStockProducts(), 0L);

        return DashboardDTO.builder()
                .totalInventoryValue(totalInventoryValue)
                .totalSalesRevenue(totalSalesRevenue)
                .totalExpenses(totalExpenses)
                .totalProfit(totalProfit)
                .totalCustomers(totalCustomers)
                .totalSuppliers(totalSuppliers)
                .outOfStockProducts(outOfStockProducts)
                .lowStockProducts(lowStockProducts)
                .build();
    }
}
