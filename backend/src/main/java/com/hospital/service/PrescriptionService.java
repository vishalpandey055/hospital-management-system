package com.hospital.service;

import com.hospital.dto.request.PrescriptionRequest;
import com.hospital.dto.response.PrescriptionResponse;
import com.hospital.entity.MedicalRecord;
import com.hospital.entity.Prescription;
import com.hospital.exception.ResourceAlreadyExistsException;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.MedicalRecordRepository;
import com.hospital.repository.PrescriptionRepository;

import org.springframework.stereotype.Service;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    public PrescriptionService(
            PrescriptionRepository prescriptionRepository,
            MedicalRecordRepository medicalRecordRepository) {

        this.prescriptionRepository = prescriptionRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    // ==============================
    // ADD PRESCRIPTION
    // ==============================
    public PrescriptionResponse addPrescription(PrescriptionRequest request) {

        MedicalRecord medicalRecord = medicalRecordRepository
                .findById(request.getMedicalRecordId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Medical record not found"));

        if (prescriptionRepository.existsByMedicalRecordIdAndMedicine(
                request.getMedicalRecordId(),
                request.getMedicine())) {

            throw new ResourceAlreadyExistsException(
                    "Medicine already prescribed for this record");
        }

        Prescription prescription = new Prescription();

        prescription.setMedicine(request.getMedicine());
        prescription.setDosage(request.getDosage());
        prescription.setInstruction(request.getInstruction());
        prescription.setMedicalRecord(medicalRecord);

        Prescription saved = prescriptionRepository.save(prescription);

        return new PrescriptionResponse(
                saved.getId(),
                saved.getMedicine(),
                saved.getDosage(),
                saved.getInstruction()
        );
    }
}