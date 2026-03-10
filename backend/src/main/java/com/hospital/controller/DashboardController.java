package com.hospital.controller;

import com.hospital.dto.response.DashboardResponse;
import com.hospital.service.DashboardService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/stats")
    public DashboardResponse getDashboardStats() {
        return dashboardService.getStats();
    }
}