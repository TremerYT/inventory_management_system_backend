package com.ims.inventory_management_system_backend.entities.purchase;

import com.ims.inventory_management_system_backend.entities.supplier.Supplier;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "purchases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference_number", nullable = false, unique = true)
    private String referenceNumber;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Column(name = "shipping", nullable = false)
    private Double shipping;

    @Column(name = "purchase_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PurchaseStatus purchaseStatus;

    @Column(name = "remarks")
    private String remarks;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseItems> purchaseItems;

    @Column(name = "sub_total", nullable = false)
    private Double subTotal;

    @Column(name = "grand_total", nullable = false)
    private Double grandTotal;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;


}
