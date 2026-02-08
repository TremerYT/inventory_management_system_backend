package com.ims.inventory_management_system_backend.service.brand;

import com.ims.inventory_management_system_backend.repository.brand.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;


}
