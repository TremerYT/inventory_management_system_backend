package com.ims.inventory_management_system_backend.controller.brand;

import com.ims.inventory_management_system_backend.dto.brand.BrandRequestDTO;
import com.ims.inventory_management_system_backend.dto.brand.BrandResponseDTO;
import com.ims.inventory_management_system_backend.service.brand.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
@RequiredArgsConstructor
public class BrandController {
    public final BrandService brandService;

    @GetMapping
    public ResponseEntity<List<BrandResponseDTO>> getAllBrands() {
        return ResponseEntity.ok(brandService.getAllBrands());
    }

    @PostMapping("/create")
    public ResponseEntity<BrandResponseDTO> createBrand(@Valid @RequestBody BrandRequestDTO request) {
        BrandResponseDTO brand = brandService.createBrand(request);
        return new ResponseEntity<>(brand, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandResponseDTO> updateBrand(@Valid @RequestBody BrandRequestDTO request, @PathVariable Long id){
        return ResponseEntity.ok(brandService.updateBrand(request, id));
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id){
        brandService.deleteBrand(id);
    }
}
