package com.ims.inventory_management_system_backend.entities.product;

import com.ims.inventory_management_system_backend.entities.brands.Brand;
import com.ims.inventory_management_system_backend.entities.category.Category;
import com.ims.inventory_management_system_backend.entities.orders.OrderItems;
import com.ims.inventory_management_system_backend.entities.purchase.PurchaseItems;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sku_number", nullable = false, unique = true, length = 30)
    private String skuNumber;

    @Column(name = "barcode_number", nullable = false, unique = true)
    private String barcodeNumber;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Column(name = "product_name", unique = true, nullable = false)
    private String productName;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "cost_price", nullable = false)
    private Double costPrice;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @Column(name = "min_stock", nullable = false)
    private Integer minStock;

    @Column(name = "discount_type", nullable = false)
    private String discountType;

    @Column(name = "discount_value", nullable = false)
    private Double discountValue;

    @Column(name = "main_image", nullable = false)
    private String mainImage;

    @Column(name = "product_images")
    private List<String> galleryImages;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "product")
    private List<OrderItems> orderItems;

    @OneToMany(mappedBy = "product")
    private List<PurchaseItems> purchaseItems;
}
