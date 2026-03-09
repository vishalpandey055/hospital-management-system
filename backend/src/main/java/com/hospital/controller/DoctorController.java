package com.hospital.controller;

import com.hospital.dto.DoctorRequest;
import com.hospital.dto.DoctorResponse;
import com.hospital.service.DoctorService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // GET ALL DOCTORS
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','PATIENT')")
    public ResponseEntity<List<DoctorResponse>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    // GET DOCTOR BY ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PATIENT')")
    public ResponseEntity<DoctorResponse> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    // CREATE DOCTOR
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DoctorResponse> createDoctor(@Valid @RequestBody DoctorRequest request) {
        return ResponseEntity.ok(doctorService.createDoctor(request));
    }

    // UPDATE DOCTOR
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DoctorResponse> updateDoctor(
            @PathVariable Long id,
            @Valid @RequestBody DoctorRequest request) {

        return ResponseEntity.ok(doctorService.updateDoctor(id, request));
    }

    // DELETE DOCTOR
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {

        doctorService.deleteDoctor(id);

        return ResponseEntity.ok("Doctor deleted successfully");
    }
}