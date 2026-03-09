package com.hospital.service;

import com.hospital.dto.DoctorRequest;
import com.hospital.dto.DoctorResponse;

import java.util.List;

public interface DoctorService {

    List<DoctorResponse> getAllDoctors();

    DoctorResponse getDoctorById(Long id);

    DoctorResponse createDoctor(DoctorRequest request);

    DoctorResponse updateDoctor(Long id, DoctorRequest request);

    void deleteDoctor(Long id);
}