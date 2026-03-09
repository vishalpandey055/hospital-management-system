package com.hospital.controller;

import com.hospital.dto.MedicalRecordRequest;
import com.hospital.dto.MedicalRecordResponse;
import com.hospital.service.MedicalRecordService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical-records")
@CrossOrigin(origins = "http://localhost:5173")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    // ==============================
    // CREATE MEDICAL RECORD
    // (Doctor only)
    // ==============================
    @PostMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<MedicalRecordResponse> createMedicalRecord(
            @Valid @RequestBody MedicalRecordRequest request) {

        MedicalRecordResponse response =
                medicalRecordService.createMedicalRecord(request);

        return ResponseEntity.ok(response);
    }

    // ==============================
    // GET PATIENT MEDICAL HISTORY
    // ==============================
    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('PATIENT','ADMIN')")
    public ResponseEntity<List<MedicalRecordResponse>> getMyMedicalHistory() {

        List<MedicalRecordResponse> records =
                medicalRecordService.getMyMedicalHistory();

        return ResponseEntity.ok(records);
    }
}