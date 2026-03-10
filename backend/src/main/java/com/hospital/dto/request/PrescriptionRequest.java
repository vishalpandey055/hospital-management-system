package com.hospital.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PrescriptionRequest {

    @NotNull
    private Long medicalRecordId;

    @NotBlank
    private String medicine;

    @NotBlank
    private String dosage;

    @NotBlank
    private String instruction;

}