package com.hospital.controller;

import com.hospital.dto.PrescriptionRequest;
import com.hospital.dto.PrescriptionResponse;
import com.hospital.service.PrescriptionService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PostMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public PrescriptionResponse create(
            @Valid @RequestBody PrescriptionRequest request) {

        return prescriptionService.addPrescription(request);
    }
}