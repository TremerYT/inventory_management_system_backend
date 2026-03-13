package com.ims.inventory_management_system_backend.repository.expense;

import com.ims.inventory_management_system_backend.entities.expense.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {
}
