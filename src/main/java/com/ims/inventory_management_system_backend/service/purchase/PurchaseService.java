package com.ims.inventory_management_system_backend.service.purchase;

import com.ims.inventory_management_system_backend.dto.purchases.PurchaseItemsRequestDTO;
import com.ims.inventory_management_system_backend.dto.purchases.PurchaseItemsResponseDTO;
import com.ims.inventory_management_system_backend.dto.purchases.PurchaseRequestDTO;
import com.ims.inventory_management_system_backend.dto.purchases.PurchaseResponseDTO;
import com.ims.inventory_management_system_backend.entities.purchase.Purchase;
import com.ims.inventory_management_system_backend.entities.purchase.PurchaseItems;
import com.ims.inventory_management_system_backend.entities.purchase.PurchaseStatus;
import com.ims.inventory_management_system_backend.repository.purchases.PurchaseRepository;
import com.ims.inventory_management_system_backend.repository.supplier.SupplierRepository;
import com.ims.inventory_management_system_backend.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    @Transactional
    public PurchaseResponseDTO createPurchase(PurchaseRequestDTO request) {
        Purchase purchase = mapRequestToEntity(request);
        Purchase savedPurchase = purchaseRepository.save(purchase);
        return mapToResponse(savedPurchase);
    }

    @Transactional(readOnly = true)
    public PurchaseResponseDTO getPurchaseById(Long id) {
        Purchase purchase = purchaseRepository.findByIdWithItemsAndProduct(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found with id: " + id));
        return mapToResponse(purchase);
    }

    @Transactional(readOnly = true)
    public List<PurchaseResponseDTO> getAllPurchases() {
        return purchaseRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public PurchaseResponseDTO updatePurchase(Long id, PurchaseRequestDTO request) {
        Purchase purchase = purchaseRepository.findById(id)
                .map(existingPurchase -> {
                    var supplier = supplierRepository.findById(request.getSupplierId())
                            .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + request.getSupplierId()));
                    
                    existingPurchase.setReferenceNumber(request.getReferenceNumber());
                    existingPurchase.setDate(request.getDate());
                    existingPurchase.setSupplier(supplier);
                    existingPurchase.setShipping(request.getShipping());
                    existingPurchase.setPaid(request.getPaid());
                    existingPurchase.setPurchaseStatus(PurchaseStatus.valueOf(request.getPurchaseStatus().toUpperCase()));
                    existingPurchase.setRemarks(request.getRemarks());
                    
                    List<PurchaseItems> items = mapItemsToEntity(request.getItems(), existingPurchase);
                    existingPurchase.setPurchaseItems(items);
                    existingPurchase.setSubTotal(calculateSubTotal(items));
                    existingPurchase.setGrandTotal(calculateGrandTotal(calculateSubTotal(items), request.getShipping()));
                    return existingPurchase;
                })
                .orElseGet(() -> {
                    Purchase newPurchase = mapRequestToEntity(request);
                    newPurchase.setId(id);
                    return newPurchase;
                });

        Purchase updatedPurchase = purchaseRepository.save(purchase);
        return mapToResponse(updatedPurchase);
    }

    @Transactional
    public void deletePurchase(Long id) {
        if (!purchaseRepository.existsById(id)) {
            throw new RuntimeException("Purchase not found with id: " + id);
        }
        purchaseRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<PurchaseItemsResponseDTO> getPurchaseItems(Long id) {
        Purchase purchase = purchaseRepository.findByIdWithItemsAndProduct(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found with id: " + id));
        return purchase.getPurchaseItems().stream()
                .map(this::mapItemsToResponse)
                .collect(Collectors.toList());
    }

    private Purchase mapRequestToEntity(PurchaseRequestDTO request) {
        var supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + request.getSupplierId()));
        
        Purchase purchase = Purchase.builder()
                .referenceNumber(request.getReferenceNumber())
                .date(request.getDate())
                .supplier(supplier)
                .shipping(request.getShipping())
                .paid(request.getPaid())
                .purchaseStatus(PurchaseStatus.valueOf(request.getPurchaseStatus().toUpperCase()))
                .remarks(request.getRemarks())
                .build();
        
        List<PurchaseItems> items = mapItemsToEntity(request.getItems(), purchase);
        double subTotal = calculateSubTotal(items);
        double grandTotal = calculateGrandTotal(subTotal, request.getShipping());
        
        purchase.setPurchaseItems(items);
        purchase.setSubTotal(subTotal);
        purchase.setGrandTotal(grandTotal);
        
        return purchase;
    }

    private PurchaseResponseDTO mapToResponse(Purchase savedPurchase) {
        return PurchaseResponseDTO.builder()
                .id(savedPurchase.getId())
                .referenceNumber(savedPurchase.getReferenceNumber())
                .date(savedPurchase.getDate())
                .supplierId(savedPurchase.getSupplier() != null ? savedPurchase.getSupplier().getId(): null)
                .supplierName(savedPurchase.getSupplier() != null ? savedPurchase.getSupplier().getFirstName() + " " + savedPurchase.getSupplier().getLastName(): null)
                .shipping(savedPurchase.getShipping())
                .paid(savedPurchase.getPaid())
                .purchaseStatus(savedPurchase.getPurchaseStatus().name())
                .remarks(savedPurchase.getRemarks())
                .subTotal(savedPurchase.getSubTotal())
                .items(savedPurchase.getPurchaseItems().stream().map(this::mapItemsToResponse).collect(Collectors.toList()))
                .grandTotal(savedPurchase.getGrandTotal())
                .build();
    }

    private PurchaseItemsResponseDTO mapItemsToResponse(PurchaseItems purchaseItems) {
        return PurchaseItemsResponseDTO.builder()
                .id(purchaseItems.getId())
                .productId(purchaseItems.getProduct().getId())
                .productName(purchaseItems.getProduct().getProductName())
                .quantity(purchaseItems.getQuantity())
                .unitPrice(purchaseItems.getUnitPrice())
                .discount(purchaseItems.getDiscount())
                .subTotal(purchaseItems.getSubTotal())
                .build();
    }

    private double calculateItemSubTotal(Integer quantity, Double unitPrice, Double discount) {
        double subtotal = (unitPrice != null ? unitPrice : 0.0) * (quantity != null ? quantity : 0);
        return subtotal - (discount != null ? discount : 0.0);
    }

    private double calculateSubTotal(List<PurchaseItems> items) {
        return items.stream()
                .mapToDouble(item -> (item.getUnitPrice() * item.getQuantity()) - (item.getDiscount() != null ? item.getDiscount() : 0))
                .sum();
    }

    private double calculateGrandTotal(double subTotal, Double shipping) {
        return subTotal + (shipping != null ? shipping : 0.0);
    }

    private List<PurchaseItems> mapItemsToEntity(List<PurchaseItemsRequestDTO> items, Purchase purchase) {
        if (items == null || items.isEmpty()) {
            throw new RuntimeException("Purchase items cannot be null or empty");
        }
        
        return items.stream().map(itemDto -> {
            var product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + itemDto.getProductId()));
            
            // Increase product quantity
            Integer currentQuantity = product.getQuantity() != null ? product.getQuantity() : 0;
            Integer purchaseQuantity = itemDto.getQuantity() != null ? itemDto.getQuantity() : 0;
            product.setQuantity(currentQuantity + purchaseQuantity);
            productRepository.save(product);
            
            return PurchaseItems.builder()
                    .purchase(purchase)
                    .product(product)
                    .quantity(itemDto.getQuantity())
                    .unitPrice(itemDto.getUnitPrice())
                    .discount(itemDto.getDiscount())
                    .subTotal(calculateItemSubTotal(itemDto.getQuantity(), itemDto.getUnitPrice(), itemDto.getDiscount()))
                    .build();
        }).collect(Collectors.toList());
    }
}
