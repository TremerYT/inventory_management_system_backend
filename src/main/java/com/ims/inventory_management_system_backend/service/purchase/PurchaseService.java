package com.ims.inventory_management_system_backend.service.purchase;

import com.ims.inventory_management_system_backend.dto.purchases.PurchaseItemsRequestDTO;
import com.ims.inventory_management_system_backend.dto.purchases.PurchaseItemsResponseDTO;
import com.ims.inventory_management_system_backend.dto.purchases.PurchaseRequestDTO;
import com.ims.inventory_management_system_backend.dto.purchases.PurchaseResponseDTO;
import com.ims.inventory_management_system_backend.entities.purchase.Purchase;
import com.ims.inventory_management_system_backend.entities.purchase.PurchaseItems;
import com.ims.inventory_management_system_backend.entities.purchase.PurchaseStatus;
import com.ims.inventory_management_system_backend.repository.purchases.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    public PurchaseResponseDTO createPurchase (PurchaseRequestDTO request) {
        Purchase purchase = mapRequestToEntity(request);
        Purchase savedPurchase = purchaseRepository.save(purchase);
        return mapToResponse(savedPurchase);
    }

    private Purchase mapRequestToEntity(PurchaseRequestDTO request) {
        return Purchase.builder()
                .referenceNumber(request.getReferenceNumber())
                .date(request.getDate())
                .shipping(request.getShipping())
                .purchaseStatus(PurchaseStatus.valueOf(request.getPurchaseStatus().toUpperCase()))
                .remarks(request.getRemarks())
                .purchaseItems(mapItemsToEntity(request.getItems()))
                .build();
    }

    private PurchaseResponseDTO mapToResponse(Purchase savedPurchase) {
        return PurchaseResponseDTO.builder()
                .id(savedPurchase.getId())
                .referenceNumber(savedPurchase.getReferenceNumber())
                .date(savedPurchase.getDate())
                .supplierId(savedPurchase.getSupplier() != null ? savedPurchase.getSupplier().getId(): null)
                .supplierName(savedPurchase.getSupplier() != null ? savedPurchase.getSupplier().getFirstName() + " " + savedPurchase.getSupplier().getLastName(): null)
                .shipping(savedPurchase.getShipping())
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
                .quantity(purchaseItems.getQuantity())
                .unitPrice(purchaseItems.getUnitPrice())
                .discount(purchaseItems.getDiscount())
                .build();
    }

    private List<PurchaseItems> mapItemsToEntity(List<PurchaseItemsRequestDTO> items) {
       return items.stream().map(item ->
            PurchaseItems.builder()
                    .quantity(item.getQuantity())
                    .unitPrice(item.getUnitPrice())
                    .discount(item.getDiscount())
                    .build()
        ).collect(Collectors.toList());
    }
}
