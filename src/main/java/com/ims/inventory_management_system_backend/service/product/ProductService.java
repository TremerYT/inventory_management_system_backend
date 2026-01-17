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

    public Category getCategory (Long categoryId) {
        return categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public ProductResponseDTO createProduct (ProductRequestDTO request) {
        Category category = getCategory(request.getCategoryId());

        Product newProduct = new  Product();
        mapRequestToProduct(request, newProduct, category);

        Product savedProduct = productRepository.save(newProduct);
        return mapToResponse(savedProduct);
    }

    public List<ProductResponseDTO> getAllProducts () {
        return productRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Product does not exist"));
        return mapToResponse(product);
    }

    public List<Product> getLowStockProducts() {
        return productRepository.findLowStockProducts();
    }

    public List<Product> getOutOfStockProducts() {
        return productRepository.findOutOfStockProducts();
    }

    public long getLowStockCount() {
        return productRepository.countLowStockProducts();
    }

    public long getOutOfStockCount() {
        return productRepository.countOutOfStockProducts();
    }

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO request) {
        Category category = getCategory(request.getCategoryId());

        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Product not Found"));
        mapRequestToProduct(request, product, category);

        Product updatedProduct = productRepository.save(product);
        return mapToResponse(updatedProduct);
    }

    public void delete (Long id){
        if (!productRepository.existsById(id)){
            throw new RuntimeException("Product Not found");
        }
        productRepository.deleteById(id);
    }

    private ProductResponseDTO mapToResponse(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setSkuNumber(product.getSkuNumber());
        dto.setBarcodeNumber(product.getBarcodeNumber());
        dto.setProductName(product.getProductName());
        dto.setBrand(product.getBrand());
        dto.setDescription(product.getDescription());
        dto.setQuantity(product.getQuantity());
        dto.setMinStock(product.getMinStock());
        dto.setCostPrice(product.getCostPrice());
        dto.setUnitPrice(product.getUnitPrice());
        dto.setDiscountType(product.getDiscountType());
        dto.setDiscountValue(product.getDiscountValue());
        dto.setMainImage(product.getMainImage());
        dto.setGalleryImages(product.getGalleryImages());
        dto.setCategoryName(product.getCategory().getCategoryName());
        return dto;
    }

    private void mapRequestToProduct(ProductRequestDTO request, Product product, Category category) {
        product.setSkuNumber(request.getSkuNumber());
        product.setBarcodeNumber(request.getBarcodeNumber());
        product.setProductName(request.getProductName());
        product.setBrand(request.getBrand());
        product.setDescription(request.getDescription());
        product.setQuantity(request.getQuantity());
        product.setMinStock(request.getMinStock());
        product.setCostPrice(request.getCostPrice());
        product.setUnitPrice(request.getUnitPrice());
        product.setDiscountType(request.getDiscountType());
        product.setDiscountValue(request.getDiscountValue());
        product.setMainImage(request.getMainImage());
        product.setGalleryImages(request.getGalleryImages());
        product.setCategory(category);
    }
}
