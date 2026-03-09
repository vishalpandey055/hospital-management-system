package com.hospital.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String specialization;
    private String availableDays;

    // Many doctors belong to one department
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @JsonIgnore
    private Department department;

    // Prevent infinite JSON recursion
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Appointment> appointments;

    // One doctor has one login user
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", unique = true)
    @JsonIgnore
    private User user;

    public Doctor() {}

    public Doctor(Long id, String name, String specialization,
                  String availableDays, Department department,
                  List<Appointment> appointments, User user) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.availableDays = availableDays;
        this.department = department;
        this.appointments = appointments;
        this.user = user;
    }

    // ======================
    // GETTERS
    // ======================

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getAvailableDays() {
        return availableDays;
    }

    public Department getDepartment() {
        return department;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public User getUser() {
        return user;
    }

    // ======================
    // SETTERS
    // ======================

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setAvailableDays(String availableDays) {
        this.availableDays = availableDays;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public void setUser(User user) {
        this.user = user;
    }
}