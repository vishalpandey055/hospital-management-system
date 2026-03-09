package com.hospital.dto;

public class PrescriptionResponse {

    private Long id;
    private String medicine;
    private String dosage;
    private String instruction;

    public PrescriptionResponse() {}

    public PrescriptionResponse(Long id,
                                String medicine,
                                String dosage,
                                String instruction) {
        this.id = id;
        this.medicine = medicine;
        this.dosage = dosage;
        this.instruction = instruction;
    }

    public Long getId() { return id; }
    public String getMedicine() { return medicine; }
    public String getDosage() { return dosage; }
    public String getInstruction() { return instruction; }

    public void setId(Long id) { this.id = id; }
    public void setMedicine(String medicine) { this.medicine = medicine; }
    public void setDosage(String dosage) { this.dosage = dosage; }
    public void setInstruction(String instruction) { this.instruction = instruction; }
}