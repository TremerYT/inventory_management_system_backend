package com.ims.inventory_management_system_backend.entities.order_items;

import com.ims.inventory_management_system_backend.entities.orders.Orders;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orderItems")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders;

    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @Column(name = "unit_cost", nullable = false)
    private Double unitCost;

    @Column(name = "discount", nullable = false)
    private Double discount;

    @Column(name = "sub_total", nullable = false)
    private Double subTotal;
}
