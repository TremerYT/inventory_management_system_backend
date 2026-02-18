package com.ims.inventory_management_system_backend.entities.customers;

import com.ims.inventory_management_system_backend.entities.address.Address;
import com.ims.inventory_management_system_backend.entities.sale.Sales;
import com.ims.inventory_management_system_backend.entities.user.User;
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

    @Column(name="user_name", nullable = false)
    private String userName;

    @Column(name="phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name="is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(updatable = true)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @OneToMany(mappedBy="customer")
    private List<Sales> sales;

    @OneToMany(mappedBy="customer", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Address> addresses;
}
