package com.ims.inventory_management_system_backend.repository.category;

import com.ims.inventory_management_system_backend.entities.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
