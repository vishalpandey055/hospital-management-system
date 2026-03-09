package com.hospital.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PrescriptionRequest {

    @NotNull(message = "Medical record ID is required")
    private Long medicalRecordId;

    @NotBlank(message = "Medicine is required")
    private String medicine;

    @NotBlank(message = "Dosage is required")
    private String dosage;

    @NotBlank(message = "Instruction is required")
    private String instruction;

    public PrescriptionRequest() {}

    public PrescriptionRequest(Long medicalRecordId,
                               String medicine,
                               String dosage,
                               String instruction) {
        this.medicalRecordId = medicalRecordId;
        this.medicine = medicine;
        this.dosage = dosage;
        this.instruction = instruction;
    }

    public Long getMedicalRecordId() { return medicalRecordId; }
    public void setMedicalRecordId(Long medicalRecordId) { this.medicalRecordId = medicalRecordId; }

    public String getMedicine() { return medicine; }
    public void setMedicine(String medicine) { this.medicine = medicine; }

    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }

    public String getInstruction() { return instruction; }
    public void setInstruction(String instruction) { this.instruction = instruction; }
}