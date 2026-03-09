package com.hospital.dto;

public class DepartmentResponse {

    private Long id;
    private String name;

    // No-Args Constructor
    public DepartmentResponse() {
    }

    // ✅ All-Args Constructor (REQUIRED)
    public DepartmentResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter
    public Long getId() {
        return id;
    }

    // Setter
    public void setId(Long id) {
        this.id = id;
    }

    // Getter
    public String getName() {
        return name;
    }

    // Setter
    public void setName(String name) {
        this.name = name;
    }
}