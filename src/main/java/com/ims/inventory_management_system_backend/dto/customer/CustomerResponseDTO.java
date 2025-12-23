package com.ims.inventory_management_system_backend.dto.customer;

import com.ims.inventory_management_system_backend.dto.address.AddressDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO {
    private Long id;
    private String customerCode;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Boolean isActive;
    private List<AddressDTO> addresses;
}
