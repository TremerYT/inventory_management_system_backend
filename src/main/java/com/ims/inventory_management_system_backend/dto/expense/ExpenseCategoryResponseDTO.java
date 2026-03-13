package com.ims.inventory_management_system_backend.dto.expense;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseCategoryResponseDTO {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
}
