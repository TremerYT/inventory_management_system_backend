package com.ims.inventory_management_system_backend.controller.purchase;

import com.ims.inventory_management_system_backend.dto.purchases.PurchaseItemsResponseDTO;
import com.ims.inventory_management_system_backend.dto.purchases.PurchaseRequestDTO;
import com.ims.inventory_management_system_backend.dto.purchases.PurchaseResponseDTO;
import com.ims.inventory_management_system_backend.service.purchase.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PostMapping("/create")
    public ResponseEntity<PurchaseResponseDTO> createPurchase(@Valid @RequestBody PurchaseRequestDTO request) {
        PurchaseResponseDTO response = purchaseService.createPurchase(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponseDTO> getPurchaseById(@PathVariable Long id) {
        PurchaseResponseDTO response = purchaseService.getPurchaseById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PurchaseResponseDTO>> getAllPurchases() {
        List<PurchaseResponseDTO> response = purchaseService.getAllPurchases();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseResponseDTO> updatePurchase(@PathVariable Long id, @Valid @RequestBody PurchaseRequestDTO request) {
        PurchaseResponseDTO response = purchaseService.updatePurchase(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Long id) {
        purchaseService.deletePurchase(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<PurchaseItemsResponseDTO>> getPurchaseItems(@PathVariable Long id) {
        List<PurchaseItemsResponseDTO> response = purchaseService.getPurchaseItems(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
