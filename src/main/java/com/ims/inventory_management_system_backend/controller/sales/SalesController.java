package com.ims.inventory_management_system_backend.controller.sales;

import com.ims.inventory_management_system_backend.dto.sales.SaleItemsResponseDTO;
import com.ims.inventory_management_system_backend.dto.sales.SaleRequestDTO;
import com.ims.inventory_management_system_backend.dto.sales.SaleResponseDTO;
import com.ims.inventory_management_system_backend.service.sales.SalesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SalesController {
    private final SalesService salesService;

    @PostMapping("/create")
    public ResponseEntity<SaleResponseDTO> createSale(@Valid @RequestBody SaleRequestDTO request) {
        SaleResponseDTO response = salesService.createSale(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> getSaleById(@PathVariable Long id) {
        SaleResponseDTO response = salesService.getSaleById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SaleResponseDTO>> getAllSales() {
        List<SaleResponseDTO> response = salesService.getAllSales();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> updateSale(@PathVariable Long id, @Valid @RequestBody SaleRequestDTO request) {
        SaleResponseDTO response = salesService.updateSale(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        salesService.deleteSale(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<SaleItemsResponseDTO>> getSaleItems(@PathVariable Long id) {
        List<SaleItemsResponseDTO> response = salesService.getSaleItems(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}