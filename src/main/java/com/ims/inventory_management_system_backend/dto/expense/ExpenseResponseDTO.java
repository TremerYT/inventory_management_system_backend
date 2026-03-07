package com.ims.inventory_management_system_backend.dto.expense;

import com.ims.inventory_management_system_backend.entities.expense.ExpenseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponseDTO {
    private Long id;
    private String expenseName;
    private String description;
    private Long categoryId;
    private String categoryName;
    private LocalDate date;
    private Double expenseAmount;
    private ExpenseStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
