package com.ims.inventory_management_system_backend.service.sales;

import com.ims.inventory_management_system_backend.dto.sales.SaleItemsRequestDTO;
import com.ims.inventory_management_system_backend.dto.sales.SaleItemsResponseDTO;
import com.ims.inventory_management_system_backend.dto.sales.SaleRequestDTO;
import com.ims.inventory_management_system_backend.dto.sales.SaleResponseDTO;
import com.ims.inventory_management_system_backend.entities.customers.Customer;
import com.ims.inventory_management_system_backend.entities.product.Product;
import com.ims.inventory_management_system_backend.entities.sale.PaymentStatus;
import com.ims.inventory_management_system_backend.entities.sale.SaleItems;
import com.ims.inventory_management_system_backend.entities.sale.SaleStatus;
import com.ims.inventory_management_system_backend.entities.sale.Sales;
import com.ims.inventory_management_system_backend.repository.customer.CustomerRepository;
import com.ims.inventory_management_system_backend.repository.product.ProductRepository;
import com.ims.inventory_management_system_backend.repository.sales.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesService {
    private final SalesRepository salesRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Transactional
    public SaleResponseDTO createSale(SaleRequestDTO request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Sales sale = Sales.builder()
                .referenceNumber(request.getReferenceNumber())
                .date(request.getDate())
                .customer(customer)
                .shipping(request.getShipping())
                .paid(request.getPaid())
                .saleStatus(SaleStatus.valueOf(request.getSaleStatus()))
                .paymentStatus(PaymentStatus.valueOf(request.getPaymentStatus()))
                .remarks(request.getRemarks())
                .build();

        List<SaleItems> items = request.getItems().stream().map(itemDto -> {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getQuantity() < itemDto.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getProductName());
            }

            product.setQuantity(product.getQuantity() - itemDto.getQuantity());
            productRepository.save(product);

            double subTotal = itemDto.getQuantity() * itemDto.getUnitPrice() - (itemDto.getDiscount() != null ? itemDto.getDiscount() : 0.0);

            return SaleItems.builder()
                    .sale(sale)
                    .product(product)
                    .quantity(itemDto.getQuantity())
                    .unitPrice(itemDto.getUnitPrice())
                    .discount(itemDto.getDiscount())
                    .subTotal(subTotal)
                    .build();
        }).collect(Collectors.toList());

        double totalSubTotal = items.stream().mapToDouble(SaleItems::getSubTotal).sum();
        double grandTotal = totalSubTotal + request.getShipping();

        sale.setSaleItems(items);
        sale.setSubTotal(totalSubTotal);
        sale.setGrandTotal(grandTotal);

        Sales savedSale = salesRepository.save(sale);
        return mapToResponse(savedSale);
    }

    private SaleResponseDTO mapToResponse(Sales sale) {
        return SaleResponseDTO.builder()
                .id(sale.getId())
                .referenceNumber(sale.getReferenceNumber())
                .date(sale.getDate())
                .customerId(sale.getCustomer().getId())
                .customerName(sale.getCustomer().getFirstName() + " " + sale.getCustomer().getLastName())
                .shipping(sale.getShipping())
                .paid(sale.getPaid())
                .saleStatus(sale.getSaleStatus().name())
                .paymentStatus(sale.getPaymentStatus().name())
                .remarks(sale.getRemarks())
                .subTotal(sale.getSubTotal())
                .grandTotal(sale.getGrandTotal())
                .items(sale.getSaleItems().stream().map(this::mapItemToResponse).collect(Collectors.toList()))
                .build();
    }

    private SaleItemsResponseDTO mapItemToResponse(SaleItems item) {
        return SaleItemsResponseDTO.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getProductName())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .discount(item.getDiscount())
                .subTotal(item.getSubTotal())
                .build();
    }
}
