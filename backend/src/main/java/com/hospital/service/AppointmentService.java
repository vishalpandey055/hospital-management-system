package com.hospital.service;

import com.hospital.dto.AppointmentRequest;
import com.hospital.dto.AppointmentResponse;
import java.util.List;

public interface AppointmentService {

    AppointmentResponse bookAppointment(AppointmentRequest request);

    // PATIENT
    List<AppointmentResponse> getMyAppointments();

    // DOCTOR
    List<AppointmentResponse> getDoctorAppointments();

    // ADMIN
    List<AppointmentResponse> getAppointmentsByDoctor(Long doctorId);
    List<AppointmentResponse> getAppointmentsByPatient(Long patientId);

    AppointmentResponse completeAppointment(Long appointmentId);
    AppointmentResponse cancelAppointment(Long appointmentId);
}