package com.hospital.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "prescription")
public class Prescription {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medicine;
    private String dosage;
    private String instruction;

    @ManyToOne
    @JoinColumn(name = "medical_record_id")
    @JsonIgnore
    private MedicalRecord medicalRecord;

    // Default Constructor
    public Prescription() {
    }

    // Full Constructor
    public Prescription(Long id, String medicine, String dosage,
                        String instruction, MedicalRecord medicalRecord) {
        this.id = id;
        this.medicine = medicine;
        this.dosage = dosage;
        this.instruction = instruction;
        this.medicalRecord = medicalRecord;
    }

    // ======================
    // GETTERS
    // ======================

    public Long getId() {
        return id;
    }

    public String getMedicine() {
        return medicine;
    }

    public String getDosage() {
        return dosage;
    }

    public String getInstruction() {
        return instruction;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    // ======================
    // SETTERS
    // ======================

    public void setId(Long id) {
        this.id = id;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }
}