package com.hospital.service;

import com.hospital.dto.*;
import com.hospital.entity.*;
import com.hospital.exception.ResourceAlreadyExistsException;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public MedicalRecordServiceImpl(
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

    // ==============================
    // CREATE MEDICAL RECORD (DOCTOR)
    // ==============================
    @Override
    public MedicalRecordResponse createMedicalRecord(MedicalRecordRequest request) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Doctor doctor = doctorRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        Appointment appointment = appointmentRepository
                .findById(request.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        if (!appointment.getDoctor().getId().equals(doctor.getId())) {
            throw new AccessDeniedException("You are not authorized for this appointment");
        }

        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new ResourceAlreadyExistsException(
                    "Appointment must be completed before creating medical record");
        }

        if (medicalRecordRepository.existsByAppointmentId(appointment.getId())) {
            throw new ResourceAlreadyExistsException(
                    "Medical record already exists for this appointment");
        }

        MedicalRecord record = new MedicalRecord();
        record.setDoctor(doctor);
        record.setPatient(appointment.getPatient());
        record.setAppointment(appointment);
        record.setDiagnosis(request.getDiagnosis());
        record.setTreatment(request.getTreatment());
        record.setVisitDate(LocalDateTime.now());

        MedicalRecord saved = medicalRecordRepository.save(record);

        return new MedicalRecordResponse(
                saved.getId(),
                appointment.getId(),
                saved.getDiagnosis(),
                saved.getTreatment(),
                List.of()   // initially no prescriptions
        );
    }

    // ==============================
    // GET PATIENT MEDICAL HISTORY
    // ==============================
    @Override
    public List<MedicalRecordResponse> getMyMedicalHistory() {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Patient patient = patientRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        return medicalRecordRepository.findByPatientId(patient.getId())
                .stream()
                .map(record -> {

                    List<PrescriptionView> prescriptionViews =
                            record.getPrescriptions()
                                    .stream()
                                    .map(p -> new PrescriptionView(
                                            p.getMedicine(),
                                            p.getDosage(),
                                            p.getInstruction()
                                    ))
                                    .toList();

                    return new MedicalRecordResponse(
                            record.getId(),
                            record.getAppointment().getId(),
                            record.getDiagnosis(),
                            record.getTreatment(),
                            prescriptionViews
                    );
                })
                .toList();
    }
}