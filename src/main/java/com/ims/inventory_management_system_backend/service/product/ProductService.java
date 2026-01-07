package com.ims.inventory_management_system_backend.service.product;

import com.ims.inventory_management_system_backend.dto.product.ProductRequestDTO;
import com.ims.inventory_management_system_backend.dto.product.ProductResponseDTO;
import com.ims.inventory_management_system_backend.entities.category.Category;
import com.ims.inventory_management_system_backend.entities.product.Product;
import com.ims.inventory_management_system_backend.repository.category.CategoryRepository;
import com.ims.inventory_management_system_backend.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Product createProduct (ProductRequestDTO request) {
        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Product product = Product
                .builder()
                .skuNumber(request.getSkuNumber())
                .productName(request.getProductName())
                .category(category)
                .brand(request.getBrand())
                .description(request.getDescription())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .discountType(request.getDiscountType())
                .discountValue(request.getDiscountValue())
                .build();

        return productRepository.save(product);
    }

    public List<ProductResponseDTO> getAllProducts () {
        return productRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ProductResponseDTO mapToResponse(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setSkuNumber(product.getSkuNumber());
        dto.setProductName(product.getProductName());
        dto.setBrand(product.getBrand());
        dto.setDescription(product.getDescription());
        dto.setQuantity(product.getQuantity());
        dto.setPrice(product.getPrice());
        dto.setDiscountType(product.getDiscountType());
        dto.setDiscountValue(product.getDiscountValue());
        dto.setProductImages(product.getProductImages());
        dto.setCategoryName(product.getCategory().getCategoryName());
        return dto;
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product does not exist"));
    }

    public Product updateProduct(Long id, ProductRequestDTO request) {
        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Product not Found"));
        product.setCategory(category);
        product.setProductName(request.getProductName());
        product.setSkuNumber(request.getSkuNumber());
        product.setBrand(request.getBrand());
        product.setDescription(request.getDescription());
        product.setQuantity(request.getQuantity());
        product.setPrice(request.getPrice());
        product.setDiscountType(request.getDiscountType());
        product.setDiscountValue(request.getDiscountValue());
        product.setProductImages(request.getProductImages());
        return productRepository.save(product);
    }

    public void delete (Long id){
        productRepository.deleteById(id);
    }
}
