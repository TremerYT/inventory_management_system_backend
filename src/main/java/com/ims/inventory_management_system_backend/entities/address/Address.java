package com.ims.inventory_management_system_backend.entities.address;

import com.ims.inventory_management_system_backend.entities.customers.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "county", nullable = false)
    private String county;

    @Column(name = "town", nullable = false)
    private String town;

    @Column(name = "estate", nullable = false)
    private String estate;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "landmark", nullable = false)
    private String landmark;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinTable(name = "customer_id")
    private Customer customer;

}
