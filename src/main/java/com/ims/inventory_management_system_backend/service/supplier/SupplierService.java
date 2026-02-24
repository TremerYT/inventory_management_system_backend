package com.ims.inventory_management_system_backend.service.supplier;

import com.ims.inventory_management_system_backend.dto.supplier.SupplierRequestDTO;
import com.ims.inventory_management_system_backend.dto.supplier.SupplierResponseDTO;
import com.ims.inventory_management_system_backend.entities.supplier.Supplier;
import com.ims.inventory_management_system_backend.repository.supplier.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public String generateSupplierCode() {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long todayCount = supplierRepository.countSupplierCreatedToday();
        String sequence = String.format("%03d", todayCount + 1);
        return "SUP-" + today + sequence;

    }

    public SupplierResponseDTO createSupplier(SupplierRequestDTO request) {
        Supplier newSupplier = new Supplier();
        mapRequestToSupplier(newSupplier, request);
        Supplier savedSupplier = supplierRepository.save(newSupplier);
        return mapSupplierToResponse(savedSupplier);
    }

    public List<SupplierResponseDTO> getAllSuppliers() {
        return supplierRepository
                .findAll()
                .stream()
                .map(this::mapSupplierToResponse)
                .toList();
    }

    public SupplierResponseDTO updateSupplier(Long id, SupplierRequestDTO request) {
        Supplier supplierToBeUpdated = supplierRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier does not exist"));
        mapRequestToSupplier(supplierToBeUpdated, request);
        Supplier updatedSupplier = supplierRepository.save(supplierToBeUpdated);
        return mapSupplierToResponse(updatedSupplier);
    }

    public void deleteSupplier (Long id) {
        supplierRepository.deleteById(id);
    }

    private void mapRequestToSupplier(Supplier supplier, SupplierRequestDTO request) {
        String supplierCode = generateSupplierCode();
        supplier.setFirstName(request.getFirstName());
        supplier.setLastName(request.getLastName());
        supplier.setSupplierCode(supplierCode);
        supplier.setEmail(request.getEmail());
        supplier.setPhoneNumber(request.getPhoneNumber());
        supplier.setCity(request.getCity());
        supplier.setZipCode(request.getZipCode());
        supplier.setAddress(request.getAddress());
        supplier.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
    }

    private SupplierResponseDTO mapSupplierToResponse(Supplier supplier) {
        return SupplierResponseDTO
                .builder()
                .id(supplier.getId())
                .firstName(supplier.getFirstName())
                .lastName(supplier.getLastName())
                .fullName(supplier.getFirstName() + " " + supplier.getLastName())
                .supplierCode(supplier.getSupplierCode())
                .email(supplier.getEmail())
                .phoneNumber(supplier.getPhoneNumber())
                .zipCode(supplier.getZipCode())
                .address(supplier.getAddress())
                .isActive(supplier.getIsActive())
                .build();
    }
}