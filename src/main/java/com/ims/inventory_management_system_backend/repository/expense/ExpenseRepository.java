package com.ims.inventory_management_system_backend.repository.expense;

import com.ims.inventory_management_system_backend.entities.expense.Expense;
import com.ims.inventory_management_system_backend.entities.expense.ExpenseCategory;
import com.ims.inventory_management_system_backend.entities.expense.ExpenseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByExpenseCategory(ExpenseCategory expenseCategory);
    
    List<Expense> findByExpenseCategoryId(Long expenseCategoryId);
    
    List<Expense> findByStatus(ExpenseStatus status);
    
    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<Expense> findByExpenseCategoryIdAndStatus(Long expenseCategoryId, ExpenseStatus status);
    
    @Query("SELECT COUNT(e) FROM Expense e")
    Long countAllExpenses();
    
    @Query("SELECT SUM(e.expenseAmount) FROM Expense e WHERE e.status = :status")
    Double sumExpensesByStatus(ExpenseStatus status);
    
    @Query("SELECT SUM(e.expenseAmount) FROM Expense e WHERE e.date BETWEEN :startDate AND :endDate")
    Double sumExpensesByDateRange(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT SUM(e.expenseAmount) FROM Expense e")
    Double sumTotalExpenses();
}
