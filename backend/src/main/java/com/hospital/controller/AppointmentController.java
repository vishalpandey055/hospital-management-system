package com.hospital.controller;

import com.hospital.dto.request.AppointmentRequest;
import com.hospital.dto.response.AppointmentResponse;
import com.hospital.service.AppointmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    // ==============================
    // BOOK APPOINTMENT (PATIENT / ADMIN)
    // ==============================
    @PostMapping
    @PreAuthorize("hasAnyRole('PATIENT','ADMIN')")
    public ResponseEntity<AppointmentResponse> bookAppointment(
            @Valid @RequestBody AppointmentRequest request) {

        AppointmentResponse response = appointmentService.bookAppointment(request);

        return ResponseEntity.ok(response);
    }

    // ==============================
    // PATIENT - MY APPOINTMENTS
    // ==============================
    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('PATIENT','ADMIN')")
    public ResponseEntity<List<AppointmentResponse>> getMyAppointments() {

        List<AppointmentResponse> appointments = appointmentService.getMyAppointments();

        return ResponseEntity.ok(appointments);
    }

    // ==============================
    // DOCTOR - MY APPOINTMENTS
    // ==============================
    @GetMapping("/doctor/my")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<List<AppointmentResponse>> getDoctorAppointments() {

        List<AppointmentResponse> appointments = appointmentService.getDoctorAppointments();

        return ResponseEntity.ok(appointments);
    }

    // ==============================
    // ADMIN - ALL APPOINTMENTS
    // ==============================
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments() {

        List<AppointmentResponse> appointments = appointmentService.getAllAppointments();

        return ResponseEntity.ok(appointments);
    }

    // ==============================
    // DOCTOR - COMPLETE APPOINTMENT
    // ==============================
    @PutMapping("/{id}/complete")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<AppointmentResponse> completeAppointment(
            @PathVariable Long id) {

        AppointmentResponse response = appointmentService.completeAppointment(id);

        return ResponseEntity.ok(response);
    }

    // ==============================
    // CANCEL APPOINTMENT
    // (PATIENT / DOCTOR / ADMIN)
    // ==============================
    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('PATIENT','ADMIN','DOCTOR')")
    public ResponseEntity<AppointmentResponse> cancelAppointment(
            @PathVariable Long id) {

        AppointmentResponse response = appointmentService.cancelAppointment(id);

        return ResponseEntity.ok(response);
    }
}