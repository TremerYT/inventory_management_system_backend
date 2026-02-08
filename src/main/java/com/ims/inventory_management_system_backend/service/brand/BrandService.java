package com.ims.inventory_management_system_backend.service.brand;

import com.ims.inventory_management_system_backend.dto.brand.BrandRequestDTO;
import com.ims.inventory_management_system_backend.dto.brand.BrandResponseDTO;
import com.ims.inventory_management_system_backend.entities.brands.Brand;
import com.ims.inventory_management_system_backend.repository.brand.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandResponseDTO createBrand (BrandRequestDTO request) {
        if (brandRepository.existsByBrandName(request.getBrandName())){
            throw new RuntimeException("Brand already exists");
        }
        Long count = brandRepository.countAllBrands() + 1;
        String brandCode = String.format("BRA-%03d", count);

        Brand newBrand = new Brand();
        newBrand.setBrandCode(brandCode);
        mapRequestToBrand(request, newBrand);

        Brand savedBrand = brandRepository.save(newBrand);
        return mapToResponse(savedBrand);
    }

    public List<BrandResponseDTO> getAllBrands () {
        return brandRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public BrandResponseDTO updateBrand (BrandRequestDTO requestDTO, Long id) {
        Brand brand = brandRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        mapRequestToBrand(requestDTO, brand);
        Brand savedBrand = brandRepository.save(brand);
        return mapToResponse(savedBrand);
    }

    public void deleteBrand (Long id) {
        Brand brand = brandRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        if (!brand.getProducts().isEmpty()){
            throw new RuntimeException("Cannot delete brand with existing products");
        }

        brandRepository.deleteById(id);
    }

    private BrandResponseDTO mapToResponse(Brand savedBrand) {
        BrandResponseDTO responseDTO = new BrandResponseDTO();
        responseDTO.setId(savedBrand.getId());
        responseDTO.setBrandCode(savedBrand.getBrandCode());
        responseDTO.setBrandName(savedBrand.getBrandName());
        responseDTO.setBrandImage(savedBrand.getBrandImage());
        responseDTO.setIsActive(savedBrand.getIsActive());
        responseDTO.setCreatedAt(savedBrand.getCreatedAt());

        return responseDTO;
    }

    private void mapRequestToBrand(BrandRequestDTO request, Brand newBrand) {
         newBrand.setBrandName(request.getBrandName());
         newBrand.setBrandImage(request.getBrandImage());
         newBrand.setIsActive(request.getIsActive());
    }
}
