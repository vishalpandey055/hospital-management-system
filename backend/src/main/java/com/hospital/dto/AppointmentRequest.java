package com.hospital.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AppointmentRequest {

    @NotNull(message = "Doctor ID is required")
    private Long doctorId;

    @NotNull(message = "Appointment date is required")
    @Future(message = "Appointment must be in future")
    private LocalDateTime appointmentDate;

    public AppointmentRequest() {}

    public AppointmentRequest(Long doctorId, LocalDateTime appointmentDate) {
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}