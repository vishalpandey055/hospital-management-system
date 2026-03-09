package com.hospital.service;

import com.hospital.dto.AppointmentRequest;
import com.hospital.dto.AppointmentResponse;
import com.hospital.entity.*;
import com.hospital.exception.ResourceAlreadyExistsException;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.*;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
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
    @Override
    public AppointmentResponse bookAppointment(AppointmentRequest request) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Patient patient = patientRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        appointmentRepository
                .findByDoctorIdAndAppointmentDate(doctor.getId(), request.getAppointmentDate())
                .ifPresent(a -> {
                    throw new ResourceAlreadyExistsException("Doctor already booked at this time");
                });

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setStatus(AppointmentStatus.BOOKED);

        return mapToResponse(appointmentRepository.save(appointment));
    }

    // ==============================
    // PATIENT APPOINTMENTS
    // ==============================
    @Override
    public List<AppointmentResponse> getMyAppointments() {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Patient patient = patientRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        return appointmentRepository.findByPatientId(patient.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==============================
    // DOCTOR APPOINTMENTS
    // ==============================
    @Override
    public List<AppointmentResponse> getDoctorAppointments() {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Doctor doctor = doctorRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        return appointmentRepository.findByDoctorId(doctor.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==============================
    // ADMIN - APPOINTMENTS BY DOCTOR
    // ==============================
    @Override
    public List<AppointmentResponse> getAppointmentsByDoctor(Long doctorId) {

        return appointmentRepository.findByDoctorId(doctorId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==============================
    // ADMIN - APPOINTMENTS BY PATIENT
    // ==============================
    @Override
    public List<AppointmentResponse> getAppointmentsByPatient(Long patientId) {

        return appointmentRepository.findByPatientId(patientId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==============================
    // COMPLETE APPOINTMENT
    // ==============================
    @Override
    public AppointmentResponse completeAppointment(Long appointmentId) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Doctor doctor = doctorRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        if (!appointment.getDoctor().getId().equals(doctor.getId())) {
            throw new AccessDeniedException("Unauthorized");
        }

        appointment.setStatus(AppointmentStatus.COMPLETED);

        return mapToResponse(appointmentRepository.save(appointment));
    }

    // ==============================
    // CANCEL APPOINTMENT
    // ==============================
    @Override
    public AppointmentResponse cancelAppointment(Long appointmentId) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        if (user.getRole() == Role.ADMIN) {

            appointment.setStatus(AppointmentStatus.CANCELLED);
            return mapToResponse(appointmentRepository.save(appointment));
        }

        if (user.getRole() == Role.PATIENT) {

            Patient patient = patientRepository.findByUser(user)
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

            if (!appointment.getPatient().getId().equals(patient.getId())) {
                throw new AccessDeniedException("Unauthorized");
            }

            appointment.setStatus(AppointmentStatus.CANCELLED);
            return mapToResponse(appointmentRepository.save(appointment));
        }

        if (user.getRole() == Role.DOCTOR) {

            Doctor doctor = doctorRepository.findByUser(user)
                    .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

            if (!appointment.getDoctor().getId().equals(doctor.getId())) {
                throw new AccessDeniedException("Unauthorized");
            }

            appointment.setStatus(AppointmentStatus.CANCELLED);
            return mapToResponse(appointmentRepository.save(appointment));
        }

        throw new AccessDeniedException("Unauthorized");
    }

    // ==============================
    // ENTITY → DTO
    // ==============================
    private AppointmentResponse mapToResponse(Appointment appointment) {

        String doctorName = appointment.getDoctor() != null
                ? appointment.getDoctor().getName()
                : null;

        String patientName = appointment.getPatient() != null
                ? appointment.getPatient().getName()
                : null;

        return new AppointmentResponse(
                appointment.getId(),
                appointment.getAppointmentDate(),
                appointment.getStatus(),
                doctorName,
                patientName
        );
    }
}