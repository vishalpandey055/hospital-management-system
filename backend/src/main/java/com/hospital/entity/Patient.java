package com.hospital.entity;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    private int age;

    private String gender;

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String email;

    // Prevent infinite JSON loop
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Appointment> appointments;

    // Default Constructor
    public Patient() {
    }

    // Full Constructor
    public Patient(Long id, String name, User user, int age, String gender,
                   String phone, String email, List<Appointment> appointments) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.appointments = appointments;
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

    public User getUser() {
        return user;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public List<Appointment> getAppointments() {
        return appointments;
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

    public void setUser(User user) {
        this.user = user;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}