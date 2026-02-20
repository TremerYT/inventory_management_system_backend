package com.ims.inventory_management_system_backend.controller.sales;

import com.ims.inventory_management_system_backend.dto.sales.SaleRequestDTO;
import com.ims.inventory_management_system_backend.dto.sales.SaleResponseDTO;
import com.ims.inventory_management_system_backend.service.sales.SalesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
