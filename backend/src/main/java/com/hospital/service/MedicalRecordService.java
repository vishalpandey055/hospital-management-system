package com.hospital.service;

import com.hospital.dto.request.MedicalRecordRequest;
import com.hospital.dto.response.MedicalRecordResponse;
import com.hospital.dto.response.PrescriptionResponse;
import com.hospital.entity.*;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.*;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public MedicalRecordService(
            MedicalRecordRepository medicalRecordRepository,
            AppointmentRepository appointmentRepository,
            DoctorRepository doctorRepository,
            PatientRepository patientRepository,
            UserRepository userRepository) {

        this.medicalRecordRepository = medicalRecordRepository;
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    // ==========================================
    // CREATE MEDICAL RECORD
    // ==========================================
    public MedicalRecordResponse createMedicalRecord(MedicalRecordRequest request) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Doctor doctor = doctorRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        Appointment appointment = appointmentRepository
                .findById(request.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        MedicalRecord record = new MedicalRecord();

        record.setDoctor(doctor);
        record.setPatient(appointment.getPatient());
        record.setAppointment(appointment);
        record.setDiagnosis(request.getDiagnosis());
        record.setTreatment(request.getTreatment());
        record.setVisitDate(LocalDateTime.now());

        MedicalRecord saved = medicalRecordRepository.save(record);

        return mapToResponse(saved);
    }

    // ==========================================
    // PATIENT HISTORY
    // ==========================================
    public List<MedicalRecordResponse> getMyMedicalHistory() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Patient patient = patientRepository
                .findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        List<MedicalRecord> records =
                medicalRecordRepository.findByPatientId(patient.getId());

        return records.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==========================================
    // DOCTOR RECORDS
    // ==========================================
    public List<MedicalRecordResponse> getDoctorMedicalRecords() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Doctor doctor = doctorRepository
                .findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        List<MedicalRecord> records =
                medicalRecordRepository.findByDoctorId(doctor.getId());

        return records.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==========================================
    // ENTITY → DTO
    // ==========================================
    private MedicalRecordResponse mapToResponse(MedicalRecord record) {

        List<PrescriptionResponse> prescriptions =
                record.getPrescriptions() != null
                        ? record.getPrescriptions()
                        .stream()
                        .map(p -> new PrescriptionResponse(
                                p.getId(),
                                p.getMedicine(),
                                p.getDosage(),
                                p.getInstruction()
                        ))
                        .toList()
                        : List.of();

        return new MedicalRecordResponse(
                record.getId(),
                record.getAppointment().getId(),
                record.getDiagnosis(),
                record.getTreatment(),
                prescriptions
        );
    }
}