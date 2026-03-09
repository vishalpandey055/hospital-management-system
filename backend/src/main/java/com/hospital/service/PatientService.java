package com.hospital.service;

import com.hospital.dto.PatientRequest;
import com.hospital.dto.PatientResponse;

import java.util.List;

public interface PatientService {

    List<PatientResponse> getAllPatients();

    PatientResponse createPatient(PatientRequest request);

    PatientResponse updatePatient(Long id, PatientRequest request);

    void deletePatient(Long id);
}