package com.ims.inventory_management_system_backend.controller.expense;

import com.ims.inventory_management_system_backend.dto.expense.ExpenseRequestDTO;
import com.ims.inventory_management_system_backend.dto.expense.ExpenseResponseDTO;
import com.ims.inventory_management_system_backend.entities.expense.ExpenseStatus;
import com.ims.inventory_management_system_backend.service.expense.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/expense")
public class ExpenseController {
    private final ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDTO>> getAllExpenses() {
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> getExpenseById(@PathVariable Long id) {
        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<ExpenseResponseDTO> createExpense(@Valid @RequestBody ExpenseRequestDTO request) {
        ExpenseResponseDTO expense = expenseService.createExpense(request);
        return new ResponseEntity<>(expense, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> updateExpense(
            @Valid @RequestBody ExpenseRequestDTO request,
            @PathVariable Long id) {
        return ResponseEntity.ok(expenseService.updateExpense(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{expenseCategoryId}")
    public ResponseEntity<List<ExpenseResponseDTO>> getExpensesByCategory(@PathVariable Long expenseCategoryId) {
        return ResponseEntity.ok(expenseService.getExpensesByCategory(expenseCategoryId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ExpenseResponseDTO>> getExpensesByStatus(@PathVariable ExpenseStatus status) {
        return ResponseEntity.ok(expenseService.getExpensesByStatus(status));
    }
}
