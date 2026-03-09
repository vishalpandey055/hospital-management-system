package com.hospital.dto;

import jakarta.validation.constraints.NotBlank;

public class DepartmentRequest {

    @NotBlank(message = "Department name cannot be empty")
    private String name;

    public DepartmentRequest() {}

    public DepartmentRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}