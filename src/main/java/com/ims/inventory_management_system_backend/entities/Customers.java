package com.ims.inventory_management_system_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_code", nullable = false, unique = true, length = 30)
    private String customerCode;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name="phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name="is_active", nullable = false)
    private Boolean isActive;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy="customer", cascade=CascadeType.ALL)
    private List<Orders> orders;

    @OneToMany(mappedBy="customer", cascade=CascadeType.ALL)
    private List<CustomerAddresses> customerAddresses;

    @OneToMany(mappedBy="customer", cascade=CascadeType.ALL)
    private List<OrderItems> orderItems;
}
