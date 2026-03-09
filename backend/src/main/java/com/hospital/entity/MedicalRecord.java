package com.hospital.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "medical_record")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diagnosis;

    private String treatment;

    private LocalDateTime visitDate;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonIgnore
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @OneToMany(mappedBy = "medicalRecord", cascade = CascadeType.ALL)
    private List<Prescription> prescriptions;

    // Default Constructor
    public MedicalRecord() {
    }

    // Full Constructor
    public MedicalRecord(Long id, String diagnosis, String treatment,
                         LocalDateTime visitDate, Doctor doctor,
                         Patient patient, Appointment appointment,
                         List<Prescription> prescriptions) {
        this.id = id;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.visitDate = visitDate;
        this.doctor = doctor;
        this.patient = patient;
        this.appointment = appointment;
        this.prescriptions = prescriptions;
    }

    // ======================
    // GETTERS
    // ======================

    public Long getId() {
        return id;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public LocalDateTime getVisitDate() {
        return visitDate;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    // ======================
    // SETTERS
    // ======================

    public void setId(Long id) {
        this.id = id;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public void setVisitDate(LocalDateTime visitDate) {
        this.visitDate = visitDate;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }
}