package com.ims.inventory_management_system_backend.entities.sale;

import com.ims.inventory_management_system_backend.entities.customers.Customer;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference_number", nullable = false, unique = true)
    private String referenceNumber;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "shipping", nullable = false)
    private Double shipping;

    @Column(name = "amount_paid", nullable = false)
    private Double paid;

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_status", nullable = false)
    private SaleStatus saleStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "remarks")
    private String remarks;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleItems> saleItems;
}
