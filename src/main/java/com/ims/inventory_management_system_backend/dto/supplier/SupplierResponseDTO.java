package com.ims.inventory_management_system_backend.dto.supplier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupplierResponseDTO {
    private Long id;
    private String supplierCode;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String zipCode;
    private String address;
    private Boolean isActive;
}