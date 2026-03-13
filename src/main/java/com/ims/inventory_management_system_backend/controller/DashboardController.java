package com.ims.inventory_management_system_backend.controller;

import com.ims.inventory_management_system_backend.dto.dashboard.DashboardDTO;
import com.ims.inventory_management_system_backend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/metrics")
    public ResponseEntity<DashboardDTO> getDashboardMetrics() {
        DashboardDTO dashboardDTO = dashboardService.getDashboardMetrics();
        return ResponseEntity.ok(dashboardDTO);
    }
}
