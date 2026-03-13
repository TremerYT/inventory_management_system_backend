package com.ims.inventory_management_system_backend.controller.expense;

import com.ims.inventory_management_system_backend.dto.expense.ExpenseCategoryRequestDTO;
import com.ims.inventory_management_system_backend.dto.expense.ExpenseCategoryResponseDTO;
import com.ims.inventory_management_system_backend.service.expense.ExpenseCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense-category")
@RequiredArgsConstructor
public class ExpenseCategoryController {

    private final ExpenseCategoryService expenseCategoryService;

    @GetMapping
    public ResponseEntity<List<ExpenseCategoryResponseDTO>> getAllExpenseCategories() {
        return ResponseEntity.ok(expenseCategoryService.getAllExpenseCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseCategoryResponseDTO> getExpenseCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(expenseCategoryService.getExpenseCategoryById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<ExpenseCategoryResponseDTO> createExpenseCategory(@Valid @RequestBody ExpenseCategoryRequestDTO expenseCategory) {
        return ResponseEntity.ok(expenseCategoryService.createExpenseCategory(expenseCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseCategoryResponseDTO> updateExpenseCategory(@PathVariable Long id, @Valid @RequestBody ExpenseCategoryRequestDTO expenseCategory) {
        return ResponseEntity.ok(expenseCategoryService.updateExpenseCategory(id, expenseCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpenseCategory(@PathVariable Long id) {
        expenseCategoryService.deleteExpenseCategory(id);
        return ResponseEntity.noContent().build();
    }
}
