package com.ims.inventory_management_system_backend.entities.customers;

import com.ims.inventory_management_system_backend.entities.sale.Sales;
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
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_code", nullable = false, unique = true, length = 30)
    private String customerCode;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @Column(name = "customer_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerCategory customerCategory;

    @Column(name="email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name="phone_number", nullable = false, unique = true, length = 20)
    private String phoneNumber;

    @Column(name="country", nullable = false)
    private String country;

    @Column(name="city", nullable = false)
    private String city;

    @Column(name="zip_code", nullable = false)
    private String zipCode;

    @Column(name="reward_points", nullable = false)
    private Integer rewardPoints;

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

    @OneToMany(mappedBy="customer")
    private List<Sales> sales;
}
