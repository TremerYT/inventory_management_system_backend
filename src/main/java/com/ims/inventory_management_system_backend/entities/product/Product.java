package com.ims.inventory_management_system_backend.entities.product;

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

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "product_name", unique = true, nullable = false)
    private String productName;

    @Column(name = "brand", unique = true, nullable = false)
    private String brand;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "discount_type", nullable = false)
    private String discountType;

    @Column(name = "discount_value", nullable = false)
    private Double discountValue;

    @Column(name = "product_images", nullable = false)
    private List<String> productImages;

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
