package com.ims.inventory_management_system_backend.service.category;

import com.ims.inventory_management_system_backend.dto.category.CategoryRequestDTO;
import com.ims.inventory_management_system_backend.dto.category.CategoryResponseDTO;
import com.ims.inventory_management_system_backend.entities.category.Category;
import com.ims.inventory_management_system_backend.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryResponseDTO createCategory(CategoryRequestDTO request) {
        if (categoryRepository.existsByCategoryName(request.getCategoryName())) {
            throw new RuntimeException("Category name already exists");
        }
        Long count = categoryRepository.countAllCategories() + 1;
        String categoryCode = String.format("CAT-%03d", count);

        Category newCategory = new Category();
        newCategory.setCategoryCode(categoryCode);
        mapRequestToCategory(request, newCategory);

        Category savedCategory = categoryRepository.save(newCategory);
        return mapToResponse(savedCategory);
    }

    public List<CategoryResponseDTO> getCategories() {
        return categoryRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO request) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        mapRequestToCategory(request, category);
        Category updatedCategory = categoryRepository.save(category);
        return  mapToResponse(updatedCategory);
    }

    public  void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    private CategoryResponseDTO mapToResponse(Category savedCategory) {
        CategoryResponseDTO responseDTO = new CategoryResponseDTO();
        responseDTO.setId(savedCategory.getId());
        responseDTO.setCategoryCode(savedCategory.getCategoryCode());
        responseDTO.setCategoryName(savedCategory.getCategoryName());
        responseDTO.setCategoryImage(savedCategory.getCategoryImage());
        responseDTO.setIsActive(savedCategory.getIsActive());
        responseDTO.setCreatedAt(savedCategory.getCreatedAt());
        return responseDTO;
    }

    private void mapRequestToCategory(CategoryRequestDTO request, Category newCategory) {
        newCategory.setCategoryName(request.getCategoryName());
        newCategory.setCategoryImage(request.getCategoryImage());
        newCategory.setIsActive(request.getIsActive());
    }
}
