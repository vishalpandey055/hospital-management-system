package com.hospital.controller;

import com.hospital.dto.AppointmentRequest;
import com.hospital.dto.AppointmentResponse;
import com.hospital.service.AppointmentService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "http://localhost:5173")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // =========================
    // BOOK APPOINTMENT
    // =========================
    @PostMapping
    @PreAuthorize("hasAnyRole('PATIENT','ADMIN')")
    public ResponseEntity<AppointmentResponse> bookAppointment(
            @Valid @RequestBody AppointmentRequest request) {

        AppointmentResponse response = appointmentService.bookAppointment(request);

        return ResponseEntity.ok(response);
    }

    // =========================
    // PATIENT APPOINTMENTS
    // =========================
    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('PATIENT','ADMIN')")
    public ResponseEntity<List<AppointmentResponse>> getMyAppointments() {

        List<AppointmentResponse> appointments =
                appointmentService.getMyAppointments();

        return ResponseEntity.ok(appointments);
    }

    // =========================
    // DOCTOR APPOINTMENTS
    // =========================
    @GetMapping("/doctor/my")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<List<AppointmentResponse>> getDoctorAppointments() {

        List<AppointmentResponse> appointments =
                appointmentService.getDoctorAppointments();

        return ResponseEntity.ok(appointments);
    }

    // =========================
    // COMPLETE APPOINTMENT
    // =========================
    @PutMapping("/{id}/complete")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<AppointmentResponse> completeAppointment(
            @PathVariable Long id) {

        AppointmentResponse response =
                appointmentService.completeAppointment(id);

        return ResponseEntity.ok(response);
    }

    // =========================
    // CANCEL APPOINTMENT
    // =========================
    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('PATIENT','ADMIN','DOCTOR')")
    public ResponseEntity<AppointmentResponse> cancelAppointment(
            @PathVariable Long id) {

        AppointmentResponse response =
                appointmentService.cancelAppointment(id);

        return ResponseEntity.ok(response);
    }
}