package com.ims.inventory_management_system_backend.entities.purchase;

import com.ims.inventory_management_system_backend.entities.purchase_items.PurchaseItems;
import com.ims.inventory_management_system_backend.entities.supplier.Supplier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "purchases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "purchase_code", nullable = false, unique = true)
    private String purchaseCode;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "sub_total", nullable = false)
    private Double subTotal;

    @Column(name = "discount", nullable = false)
    private Double discount;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseItems> purchaseItems;

    @ManyToOne(optional = false)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

}
