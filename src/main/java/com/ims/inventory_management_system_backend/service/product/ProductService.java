package com.ims.inventory_management_system_backend.service.product;

import com.ims.inventory_management_system_backend.dto.product.ProductRequestDTO;
import com.ims.inventory_management_system_backend.dto.product.ProductResponseDTO;
import com.ims.inventory_management_system_backend.entities.brands.Brand;
import com.ims.inventory_management_system_backend.entities.category.Category;
import com.ims.inventory_management_system_backend.entities.product.Product;
import com.ims.inventory_management_system_backend.repository.brand.BrandRepository;
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
    private final BrandRepository brandRepository;

    public Category getCategory (Long categoryId) {
        return categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Brand getBrand (Long brandId) {
        return brandRepository
                .findById(brandId)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
    }

    public ProductResponseDTO createProduct (ProductRequestDTO request) {
        Category category = getCategory(request.getCategoryId());
        Brand brand = getBrand(request.getBrandId());

        Product newProduct = new  Product();
        mapRequestToProduct(request, newProduct, category, brand);

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

    public List<ProductResponseDTO> searchProducts(String query) {
        return productRepository
                .searchProducts(query)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    public List<ProductResponseDTO> getLowStockProducts() {
        return productRepository
                .findLowStockProducts()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<ProductResponseDTO> getOutOfStockProducts() {
        return productRepository
                .findOutOfStockProducts()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public long getLowStockCount() {
        return productRepository.countLowStockProducts();
    }

    public long getOutOfStockCount() {
        return productRepository.countOutOfStockProducts();
    }

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO request) {
        Category category = getCategory(request.getCategoryId());
        Brand brand = getBrand(request.getBrandId());

        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Product not Found"));
        mapRequestToProduct(request, product, category, brand);

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
        dto.setId(product.getId());
        dto.setSkuNumber(product.getSkuNumber());
        dto.setBarcodeNumber(product.getBarcodeNumber());
        dto.setProductName(product.getProductName());
        dto.setBrandName(product.getBrand().getBrandName());
        dto.setDescription(product.getDescription());
        dto.setQuantity(product.getQuantity());
        dto.setUnit(product.getUnit());
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

    private void mapRequestToProduct(ProductRequestDTO request, Product product, Category category, Brand brand) {
        product.setSkuNumber(request.getSkuNumber());
        product.setBarcodeNumber(request.getBarcodeNumber());
        product.setProductName(request.getProductName());
        product.setBrand(brand);
        product.setDescription(request.getDescription());
        product.setQuantity(request.getQuantity());
        product.setUnit(request.getUnit());
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
