package com.hospital.controller;

import com.hospital.dto.request.MedicalRecordRequest;
import com.hospital.dto.response.MedicalRecordResponse;
import com.hospital.service.MedicalRecordService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    // Doctor creates record
    @PostMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<MedicalRecordResponse> createMedicalRecord(
            @Valid @RequestBody MedicalRecordRequest request) {

        return ResponseEntity.ok(
                medicalRecordService.createMedicalRecord(request)
        );
    }

    // Patient medical history
    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('PATIENT','ADMIN')")
    public ResponseEntity<List<MedicalRecordResponse>> getMyMedicalHistory() {

        return ResponseEntity.ok(
                medicalRecordService.getMyMedicalHistory()
        );
    }

    // Doctor dashboard records
    @GetMapping("/doctor")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<List<MedicalRecordResponse>> getDoctorMedicalRecords() {

        return ResponseEntity.ok(
                medicalRecordService.getDoctorMedicalRecords()
        );
    }
}