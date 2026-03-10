package com.hospital.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DoctorRequest {

    @NotBlank
    private String name;

    private String specialization;

    private String availableDays;

    @NotNull
    private Long departmentId;

    private String username;

    private String password;

}