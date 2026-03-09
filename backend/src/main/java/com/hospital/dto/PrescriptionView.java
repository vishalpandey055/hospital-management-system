package com.hospital.dto;

public class PrescriptionView {

    private String medicine;
    private String dosage;
    private String instruction;

    // No-Args Constructor
    public PrescriptionView() {
    }

    // All-Args Constructor
    public PrescriptionView(String medicine, String dosage, String instruction) {
        this.medicine = medicine;
        this.dosage = dosage;
        this.instruction = instruction;
    }

    // Getters & Setters
    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}