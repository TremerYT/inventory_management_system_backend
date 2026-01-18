package com.ims.inventory_management_system_backend.repository.category;

import com.ims.inventory_management_system_backend.entities.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCategoryName(String categoryName);

    @Query("SELECT COUNT(c) FROM Category c")
    Long countAllCategories();

    List<Category> id(Long id);
}
