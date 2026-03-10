package com.hospital.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrescriptionResponse {

    private Long id;

    private String medicine;

    private String dosage;

    private String instruction;

}