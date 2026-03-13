package com.ims.inventory_management_system_backend.service.sales;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@Service
@RequiredArgsConstructor
public class SalesService {
    private final SalesRepository salesRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<SaleResponseDTO> getAllSales() {
        return salesRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SaleResponseDTO getSaleById(Long id) {
        return mapToResponse(salesRepository.findByIdWithItemsAndProduct(id)
                .orElseThrow(() -> new RuntimeException("Sale not found")));
    }

    @Transactional(readOnly = true)
    public List<SaleItemsResponseDTO> getSaleItems(Long saleId) {
        return salesRepository.findByIdWithItemsAndProduct(saleId)
                .orElseThrow(() -> new RuntimeException("Sale not found"))
                .getSaleItems()
                .stream()
                .map(this::mapItemToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public SaleResponseDTO createSale(SaleRequestDTO request) {
        Sales sale = mapRequestToEntity(request);
        Sales savedSale = salesRepository.save(sale);
        return mapToResponse(savedSale);
    }

    @Transactional
    public SaleResponseDTO updateSale(Long id, SaleRequestDTO request) {
        Sales sale = salesRepository.findById(id)
                .map(existingSale -> {
                    Customer customer = customerRepository.findById(request.getCustomerId())
                            .orElseThrow(() -> new RuntimeException("Customer not found with id: " + request.getCustomerId()));
                    
                    existingSale.setReferenceNumber(request.getReferenceNumber());
                    existingSale.setDate(request.getDate());
                    existingSale.setCustomer(customer);
                    existingSale.setShipping(request.getShipping());
                    existingSale.setPaid(request.getPaid());
                    existingSale.setSaleStatus(SaleStatus.valueOf(request.getSaleStatus().toUpperCase()));
                    existingSale.setPaymentStatus(PaymentStatus.valueOf(request.getPaymentStatus().toUpperCase()));
                    existingSale.setRemarks(request.getRemarks());
                    
                    List<SaleItems> items = mapItemsToEntity(request.getItems(), existingSale);
                    existingSale.setSaleItems(items);
                    existingSale.setSubTotal(calculateSubTotal(items));
                    existingSale.setGrandTotal(calculateGrandTotal(calculateSubTotal(items), request.getShipping()));
                    return existingSale;
                })
                .orElseGet(() -> {
                    Sales newSale = mapRequestToEntity(request);
                    newSale.setId(id);
                    return newSale;
                });

        Sales updatedSale = salesRepository.save(sale);
        return mapToResponse(updatedSale);
    }

    @Transactional
    public void deleteSale(Long id) {
        salesRepository.deleteById(id);
    }

    private Sales mapRequestToEntity(SaleRequestDTO request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + request.getCustomerId()));
        
        Sales sale = Sales.builder()
                .referenceNumber(request.getReferenceNumber())
                .date(request.getDate())
                .customer(customer)
                .shipping(request.getShipping())
                .paid(request.getPaid())
                .saleStatus(SaleStatus.valueOf(request.getSaleStatus().toUpperCase()))
                .paymentStatus(PaymentStatus.valueOf(request.getPaymentStatus().toUpperCase()))
                .remarks(request.getRemarks())
                .build();
        
        List<SaleItems> items = mapItemsToEntity(request.getItems(), sale);
        double subTotal = calculateSubTotal(items);
        double grandTotal = calculateGrandTotal(subTotal, request.getShipping());
        
        sale.setSaleItems(items);
        sale.setSubTotal(subTotal);
        sale.setGrandTotal(grandTotal);
        
        return sale;
    }

    private List<SaleItems> mapItemsToEntity(List<SaleItemsRequestDTO> items, Sales sale) {
        return items.stream().map(itemDto -> {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + itemDto.getProductId()));
            
            // Decrease product quantity
            Integer currentQuantity = product.getQuantity() != null ? product.getQuantity() : 0;
            Integer saleQuantity = itemDto.getQuantity() != null ? itemDto.getQuantity() : 0;
            
            if (currentQuantity < saleQuantity) {
                throw new RuntimeException("Insufficient stock for product: " + product.getProductName() + 
                        ". Available: " + currentQuantity + ", Required: " + saleQuantity);
            }
            
            product.setQuantity(currentQuantity - saleQuantity);
            productRepository.save(product);
            
            return SaleItems.builder()
                    .sale(sale)
                    .product(product)
                    .quantity(itemDto.getQuantity())
                    .unitPrice(itemDto.getUnitPrice())
                    .discount(itemDto.getDiscount())
                    .subTotal(calculateItemSubTotal(itemDto.getQuantity(), itemDto.getUnitPrice(), itemDto.getDiscount()))
                    .build();
        }).collect(Collectors.toList());
    }

    private SaleResponseDTO mapToResponse(Sales sale) {
        return SaleResponseDTO.builder()
                .id(sale.getId())
                .referenceNumber(sale.getReferenceNumber())
                .date(sale.getDate())
                .customerId(sale.getCustomer() != null ? sale.getCustomer().getId() : null)
                .customerName(sale.getCustomer() != null ? sale.getCustomer().getFirstName() + " " + sale.getCustomer().getLastName() : null)
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

    private double calculateItemSubTotal(Integer quantity, Double unitPrice, Double discount) {
        double subtotal = (unitPrice != null ? unitPrice : 0.0) * (quantity != null ? quantity : 0);
        return subtotal - (discount != null ? discount : 0.0);
    }

    private double calculateSubTotal(List<SaleItems> items) {
        return items.stream()
                .mapToDouble(item -> (item.getUnitPrice() * item.getQuantity()) - (item.getDiscount() != null ? item.getDiscount() : 0))
                .sum();
    }

    private double calculateGrandTotal(double subTotal, Double shipping) {
        return subTotal + (shipping != null ? shipping : 0.0);
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