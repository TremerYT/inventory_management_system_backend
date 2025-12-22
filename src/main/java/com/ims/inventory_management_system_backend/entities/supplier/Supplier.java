package com.ims.inventory_management_system_backend.entities.supplier;

import com.ims.inventory_management_system_backend.entities.address.Address;
import com.ims.inventory_management_system_backend.entities.purchase.Purchase;
import com.ims.inventory_management_system_backend.entities.returns.PurchaseReturn;
import com.ims.inventory_management_system_backend.entities.returns.PurchaseReturnItems;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
@NoArgsConstructor
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "supplier_code", nullable = false, unique = true, length = 30)
    private String supplierCode;

    @Column(name="supplier_name", nullable = false)
    private String supplierName;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name="phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> supplierAddresses;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseReturn> purchaseReturns;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Purchase> purchases;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(updatable = true)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
