package com.hospital.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MedicalRecordResponse {

    private Long id;

    private Long appointmentId;

    private String diagnosis;

    private String treatment;

    private List<PrescriptionResponse> prescriptions;

}