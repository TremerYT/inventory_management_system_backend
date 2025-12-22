package com.ims.inventory_management_system_backend.entities.returns;

import com.ims.inventory_management_system_backend.entities.customers.Customer;
import com.ims.inventory_management_system_backend.entities.orders.Orders;
import com.ims.inventory_management_system_backend.entities.purchase.Purchase;
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
@Table(name = "purchase_return")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseReturn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @OneToMany(mappedBy = "purchaseReturn", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseReturnItems> purchaseReturnItems;

    @Column(name = "return_date",nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime returnDate;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "sub_total", nullable = false)
    private Double subTotal;
}
