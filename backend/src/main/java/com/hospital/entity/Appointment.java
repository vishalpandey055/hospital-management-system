package com.hospital.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "appointment")
public class Appointment {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime appointmentDate;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonIgnore
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    private Patient patient;

    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL)
    private MedicalRecord medicalRecord;

    public Appointment() {
    }

    public Appointment(Long id, LocalDateTime appointmentDate, AppointmentStatus status,
                       Doctor doctor, Patient patient, MedicalRecord medicalRecord) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.doctor = doctor;
        this.patient = patient;
        this.medicalRecord = medicalRecord;
    }

    // ======================
    // GETTERS
    // ======================

    public Long getId() {
        return id;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
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

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }
}