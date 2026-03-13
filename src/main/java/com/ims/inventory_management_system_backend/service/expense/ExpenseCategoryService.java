package com.ims.inventory_management_system_backend.service.expense;

import com.ims.inventory_management_system_backend.dto.expense.ExpenseCategoryRequestDTO;
import com.ims.inventory_management_system_backend.dto.expense.ExpenseCategoryResponseDTO;
import com.ims.inventory_management_system_backend.entities.expense.ExpenseCategory;
import com.ims.inventory_management_system_backend.repository.expense.ExpenseCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseCategoryService {

    private final ExpenseCategoryRepository expenseCategoryRepository;

    public List<ExpenseCategoryResponseDTO> getAllExpenseCategories() {
        return expenseCategoryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ExpenseCategoryResponseDTO getExpenseCategoryById(Long id) {
        ExpenseCategory expenseCategory = expenseCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense category not found with id: " + id));
        return mapToResponse(expenseCategory);
    }

    public ExpenseCategoryResponseDTO createExpenseCategory(ExpenseCategoryRequestDTO request) {
        ExpenseCategory expenseCategory = ExpenseCategory.builder()
                .name(request.getName())
                .build();
        
        ExpenseCategory savedExpenseCategory = expenseCategoryRepository.save(expenseCategory);
        return mapToResponse(savedExpenseCategory);
    }

    public ExpenseCategoryResponseDTO updateExpenseCategory(Long id, ExpenseCategoryRequestDTO request) {
        ExpenseCategory expenseCategory = getExpenseCategoryEntityById(id);
        expenseCategory.setName(request.getName());
        ExpenseCategory updatedExpenseCategory = expenseCategoryRepository.save(expenseCategory);
        return mapToResponse(updatedExpenseCategory);
    }

    public void deleteExpenseCategory(Long id) {
        ExpenseCategory expenseCategory = getExpenseCategoryEntityById(id);
        expenseCategoryRepository.delete(expenseCategory);
    }

    private ExpenseCategory getExpenseCategoryEntityById(Long id) {
        return expenseCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense category not found with id: " + id));
    }

    private ExpenseCategoryResponseDTO mapToResponse(ExpenseCategory expenseCategory) {
        return ExpenseCategoryResponseDTO.builder()
                .id(expenseCategory.getId())
                .name(expenseCategory.getName())
                .createdAt(expenseCategory.getCreatedAt())
                .build();
    }
}
