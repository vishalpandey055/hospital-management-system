package com.hospital.service;

import com.hospital.dto.MedicalRecordRequest;
import com.hospital.dto.MedicalRecordResponse;

import java.util.List;

public interface MedicalRecordService {

    MedicalRecordResponse createMedicalRecord(MedicalRecordRequest request);

    // 🔥 ADD THIS METHOD
    List<MedicalRecordResponse> getMyMedicalHistory();
}