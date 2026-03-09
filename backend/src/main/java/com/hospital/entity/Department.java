package com.hospital.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "department")
public class Department {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Doctor> doctors;

    public Department() {
    }

    public Department(Long id, String name, List<Doctor> doctors) {
        this.id = id;
        this.name = name;
        this.doctors = doctors;
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

    public List<Doctor> getDoctors() {
        return doctors;
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

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }
}