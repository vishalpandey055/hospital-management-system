
package com.hospital.service;

import com.hospital.dto.request.AppointmentRequest;
import com.hospital.dto.response.AppointmentResponse;
import com.hospital.entity.*;
import com.hospital.exception.ResourceAlreadyExistsException;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.*;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            DoctorRepository doctorRepository,
            PatientRepository patientRepository,
            UserRepository userRepository) {

        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    // ==============================
    // BOOK APPOINTMENT
    // ==============================
    public AppointmentResponse bookAppointment(AppointmentRequest request) {

        Patient patient = getCurrentPatient();

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        appointmentRepository
                .findByDoctorIdAndAppointmentDate(
                        doctor.getId(),
                        request.getAppointmentDate())
                .ifPresent(a -> {
                    throw new ResourceAlreadyExistsException(
                            "Doctor already booked at this time");
                });

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setStatus(AppointmentStatus.BOOKED);

        Appointment saved = appointmentRepository.save(appointment);

        return mapToResponse(saved);
    }

    // ==============================
    // PATIENT APPOINTMENTS
    // ==============================
    public List<AppointmentResponse> getMyAppointments() {

        Patient patient = getCurrentPatient();

        return appointmentRepository
                .findByPatientIdOrderByAppointmentDateDesc(patient.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==============================
    // DOCTOR APPOINTMENTS
    // ==============================
    public List<AppointmentResponse> getDoctorAppointments() {

        Doctor doctor = getCurrentDoctor();

        return appointmentRepository
                .findByDoctorIdOrderByAppointmentDateDesc(doctor.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==============================
    // ADMIN - ALL APPOINTMENTS
    // ==============================
    public List<AppointmentResponse> getAllAppointments() {

        List<Appointment> appointments = appointmentRepository.findAll();

        return appointments.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==============================
    // COMPLETE APPOINTMENT
    // ==============================
    public AppointmentResponse completeAppointment(Long id) {

        Doctor doctor = getCurrentDoctor();
        Appointment appointment = getAppointment(id);

        if (!appointment.getDoctor().getId().equals(doctor.getId())) {
            throw new AccessDeniedException("Unauthorized");
        }

        appointment.setStatus(AppointmentStatus.COMPLETED);

        Appointment updated = appointmentRepository.save(appointment);

        return mapToResponse(updated);
    }

    // ==============================
    // CANCEL APPOINTMENT
    // ==============================
    public AppointmentResponse cancelAppointment(Long id) {

        User user = getCurrentUser();
        Appointment appointment = getAppointment(id);

        if (user.getRole() == Role.ADMIN) {

            appointment.setStatus(AppointmentStatus.CANCELLED);

            return mapToResponse(
                    appointmentRepository.save(appointment)
            );
        }

        if (user.getRole() == Role.PATIENT) {

            Patient patient = getCurrentPatient();

            if (!appointment.getPatient().getId().equals(patient.getId())) {
                throw new AccessDeniedException("Unauthorized");
            }
        }

        if (user.getRole() == Role.DOCTOR) {

            Doctor doctor = getCurrentDoctor();

            if (!appointment.getDoctor().getId().equals(doctor.getId())) {
                throw new AccessDeniedException("Unauthorized");
            }
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);

        Appointment updated = appointmentRepository.save(appointment);

        return mapToResponse(updated);
    }

    // ==============================
    // HELPER METHODS
    // ==============================

    private User getCurrentUser() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    private Patient getCurrentPatient() {

        User user = getCurrentUser();

        return patientRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found"));
    }

    // ⭐ FIXED METHOD
    private Doctor getCurrentDoctor() {

        User user = getCurrentUser();

        return doctorRepository
                .findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found for user: " + user.getUsername()));
    }

    private Appointment getAppointment(Long id) {

        return appointmentRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Appointment not found"));
    }

    // ==============================
    // ENTITY → DTO
    // ==============================
    private AppointmentResponse mapToResponse(Appointment appointment) {

        String doctorName = "Unknown Doctor";
        String patientName = "Unknown Patient";

        if (appointment.getDoctor() != null
                && appointment.getDoctor().getName() != null) {

            doctorName = appointment.getDoctor().getName();
        }

        if (appointment.getPatient() != null
                && appointment.getPatient().getName() != null) {

            patientName = appointment.getPatient().getName();
        }

        return new AppointmentResponse(
                appointment.getId(),
                appointment.getAppointmentDate(),
                appointment.getStatus(),
                doctorName,
                patientName
        );
    }
}

