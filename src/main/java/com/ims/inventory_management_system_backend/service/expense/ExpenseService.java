package com.ims.inventory_management_system_backend.service.expense;

import com.ims.inventory_management_system_backend.dto.expense.ExpenseRequestDTO;
import com.ims.inventory_management_system_backend.dto.expense.ExpenseResponseDTO;
import com.ims.inventory_management_system_backend.entities.category.Category;
import com.ims.inventory_management_system_backend.entities.expense.Expense;
import com.ims.inventory_management_system_backend.entities.expense.ExpenseStatus;
import com.ims.inventory_management_system_backend.repository.category.CategoryRepository;
import com.ims.inventory_management_system_backend.repository.expense.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;

    public ExpenseResponseDTO createExpense(ExpenseRequestDTO request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Expense expense = Expense.builder()
                .expenseName(request.getExpenseName())
                .description(request.getDescription())
                .category(category)
                .date(request.getDate())
                .expenseAmount(request.getExpenseAmount())
                .status(request.getStatus())
                .build();

        Expense savedExpense = expenseRepository.save(expense);
        return mapToResponse(savedExpense);
    }

    public List<ExpenseResponseDTO> getAllExpenses() {
        return expenseRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ExpenseResponseDTO getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        return mapToResponse(expense);
    }

    public ExpenseResponseDTO updateExpense(Long id, ExpenseRequestDTO request) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        expense.setExpenseName(request.getExpenseName());
        expense.setDescription(request.getDescription());
        expense.setCategory(category);
        expense.setDate(request.getDate());
        expense.setExpenseAmount(request.getExpenseAmount());
        expense.setStatus(request.getStatus());

        Expense updatedExpense = expenseRepository.save(expense);
        return mapToResponse(updatedExpense);
    }

    public void deleteExpense(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new RuntimeException("Expense not found");
        }
        expenseRepository.deleteById(id);
    }

    public List<ExpenseResponseDTO> getExpensesByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        
        return expenseRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<ExpenseResponseDTO> getExpensesByStatus(ExpenseStatus status) {
        return expenseRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ExpenseResponseDTO mapToResponse(Expense expense) {
        return ExpenseResponseDTO.builder()
                .id(expense.getId())
                .expenseName(expense.getExpenseName())
                .description(expense.getDescription())
                .categoryId(expense.getCategory().getId())
                .categoryName(expense.getCategory().getCategoryName())
                .date(expense.getDate())
                .expenseAmount(expense.getExpenseAmount())
                .status(expense.getStatus())
                .createdAt(expense.getCreatedAt())
                .updatedAt(expense.getUpdatedAt())
                .build();
    }
}
