package com.ims.inventory_management_system_backend.entities.supplier;

import com.ims.inventory_management_system_backend.entities.purchase.Purchase;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "supplier_code", nullable = false, unique = true, length = 30)
    private String supplierCode;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name="email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name="phone_number", nullable = false, unique = true, length = 20)
    private String phoneNumber;

    @Column(name="city", nullable = false)
    private String city;

    @Column(name="zip_code", nullable = false)
    private String zipCode;

    @Column(name="address", nullable = false)
    private String address;

    @Column(name="is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy="supplier")
    private List<Purchase> purchases;
}
