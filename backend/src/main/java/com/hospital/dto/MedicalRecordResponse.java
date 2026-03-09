package com.hospital.dto;

import java.util.List;

public class MedicalRecordResponse {

    private Long id;
    private Long appointmentId;
    private String diagnosis;
    private String treatment;
    private List<PrescriptionView> prescriptions;

    public MedicalRecordResponse(Long id,
                                 Long appointmentId,
                                 String diagnosis,
                                 String treatment,
                                 List<PrescriptionView> prescriptions) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.prescriptions = prescriptions;
    }

    public Long getId() { return id; }
    public Long getAppointmentId() { return appointmentId; }
    public String getDiagnosis() { return diagnosis; }
    public String getTreatment() { return treatment; }
    public List<PrescriptionView> getPrescriptions() { return prescriptions; }
}